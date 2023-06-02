package hr.fer.zemris.java.fractals.NewtonParallel;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static hr.fer.zemris.java.fractals.Newton.Newton.calculate;
import static hr.fer.zemris.java.fractals.Newton.Newton.toComplex;

public class NewtonParallel {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<Complex> complexes = new LinkedList<>();
        int broj = 1;
        int brojDretvi, brojTraka;
        while (true) {
            System.out.print("Root " + broj + "> ");
            String ulaz = scanner.nextLine();
            if (ulaz.equals("done")) {
                if (complexes.size() < 2) {
                    System.out.println("Morate unjeti barem dvije nultocke");
                    continue;
                }
                break;
            }
            Complex c = toComplex(ulaz);
            complexes.add(c);
            broj++;
        }
        System.out.println("Unesite broj dretvi: ");
        brojDretvi = scanner.nextInt();
        System.out.println("Unesite broj traka: ");
        brojTraka = scanner.nextInt();
        System.out.print("Image of the fractal will appear shortly. Thank you.");
        scanner.close();
        System.out.println(args.length);
        FractalViewer.show(new MojProducer(brojDretvi, brojTraka, complexes));
    }
    public static class PosaoIzracuna implements Runnable {
        double reMin;
        double reMax;
        double imMin;
        double imMax;
        int width;
        int height;
        int yMin;
        int yMax;
        short[] data;
        AtomicBoolean cancel;
        ComplexRootedPolynomial complexRootedPolynomial;
        public static PosaoIzracuna NO_JOB = new PosaoIzracuna();

        private PosaoIzracuna() {
        }

        public PosaoIzracuna(double reMin, double reMax, double imMin,
                             double imMax, int width, int height, int yMin, int yMax,
                             short[] data, AtomicBoolean cancel, ComplexRootedPolynomial polynomial) {
            super();
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.data = data;
            this.cancel = cancel;
            complexRootedPolynomial= polynomial;
        }

        @Override
        public void run() {

            calculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel,
                    complexRootedPolynomial, 0.001, 0.002);

        }
    }


    public static class MojProducer implements IFractalProducer {
        private int brojDretvi, brojTraka;
        private ComplexRootedPolynomial complexRootedPolynomial;

        public MojProducer(int brojDretvi, int brojTraka, List<Complex> complexList) {
            this.brojDretvi = brojDretvi;
            this.brojTraka = brojTraka;
            Complex[] complexes = new Complex[complexList.size()];
            for (int i = 0; i < complexList.size(); i++) {
                complexes[i] = complexList.get(i);
            }
            complexRootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, complexes);
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo,
                            IFractalResultObserver observer, AtomicBoolean cancel) {
            System.out.println("Zapocinjem izracun...");
            int m = 16*16*16;
            short[] data = new short[width * height];
            int brojYPoTraci = height / brojTraka;

            final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

            Thread[] radnici = new Thread[brojDretvi];
            for(int i = 0; i < radnici.length; i++) {
                radnici[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            PosaoIzracuna p = null;
                            try {
                                p = queue.take();
                                if(p==PosaoIzracuna.NO_JOB) break;
                            } catch (InterruptedException e) {
                                continue;
                            }
                            p.run();
                        }
                    }
                });
            }
            for(int i = 0; i < radnici.length; i++) {
                radnici[i].start();
            }

            for(int i = 0; i < brojTraka; i++) {
                int yMin = i*brojYPoTraci;
                int yMax = (i+1)*brojYPoTraci-1;
                if(i==brojTraka-1) {
                    yMax = height-1;
                }
                PosaoIzracuna posao =
                        new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel,
                                complexRootedPolynomial);
                while(true) {
                    try {
                        queue.put(posao);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }
            for(int i = 0; i < radnici.length; i++) {
                while(true) {
                    try {
                        queue.put(PosaoIzracuna.NO_JOB);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            for(int i = 0; i < radnici.length; i++) {
                while(true) {
                    try {
                        radnici[i].join();
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short)m, requestNo);
        }
    }
}
