package hr.fer.zemris.java.fractals.Newton;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Newton {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Complex> complexes = new LinkedList<>();
        int broj = 1;
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
        System.out.print("Image of the fractal will appear shortly. Thank you.");
        scanner.close();
        FractalViewer.show(new MojProducer(complexes));
    }


    private static boolean isDigit(char znak) {
        return znak >= '0' && znak <= '9';
    }

    private static boolean isBlank(char znak) {
        return znak == ' ' || znak == '\t';
    }

    public static Complex toComplex(String ulaz) {
        Double re = null, im = null;
        boolean complex = false, uBroju = false, imaPredznak = false, tocka = false;
        String broj = "";
        for (int i = 0; i < ulaz.length(); i++) {
            char znak = ulaz.charAt(i);
            if (isDigit(znak)) {
                tocka = false;
                uBroju = true;
                broj += znak;
            } else if (znak == '-' || znak == '+') {
                if (imaPredznak && !uBroju)
                    throw new IllegalArgumentException("Broj ne moze imati vise od jednog predznaka.");
                if (tocka) throw new IllegalArgumentException("Morate unjet valjan decimalni broj.");
                if (uBroju) {
                    if (!complex)
                        re = Double.valueOf(broj);
                    else {
                        if (broj.equals("-"))
                            im = Double.valueOf(-1);
                        else if (broj.equals("+") || broj.isEmpty())
                            im = Double.valueOf(1);
                        else
                            im = Double.valueOf(broj);
                    }
                    broj = "";
                }
                complex = false;
                uBroju = false;
                imaPredznak = true;
                broj += znak;
            } else if (znak == 'i') {
                if (tocka) throw new IllegalArgumentException("Unesite valjani decimalni broj.");
                complex = true;
            }
            else if (isBlank(znak)) {
                if (tocka) throw new IllegalArgumentException("Unesite valjani decimalni broj.");
                if (uBroju) {
                    if (!complex)
                        re = Double.valueOf(broj);
                    else {
                        if (broj.equals("-"))
                            im = Double.valueOf(-1);
                        else if (broj.equals("+") || broj.isEmpty())
                            im = Double.valueOf(1);
                        else
                            im = Double.valueOf(broj);
                    }
                    imaPredznak = false;
                    complex = false;
                    uBroju = false;
                    broj = "";
                }
            } else if (znak == '.') {
                if (tocka) throw new IllegalArgumentException("Krivi unos broja.");
                if (!uBroju) throw new IllegalArgumentException("Unesite ispravan decimalni broj.");
                tocka = true;
                broj += znak;
            } else
                throw new IllegalArgumentException("Unesen je ne dozvoljeni charachter.");
        }
        if (!broj.isEmpty()) {
            if (!complex) {
                if (broj.equals("+") || broj.equals("-"))
                    throw new IllegalArgumentException("Morate unjeti ispravan broj");
                re = Double.valueOf(broj);
            }
            else {
                if (broj.equals("-"))
                    im = Double.valueOf(-1);
                else if (broj.equals("+"))
                    im = Double.valueOf(1);
                else
                    im = Double.valueOf(broj);
            }
        } else if (complex) {
            im = Double.valueOf(1);
        }
        double rem = re == null ? 0 : re.doubleValue(),
                imm = im == null ? 0 : im.doubleValue();
        return new Complex(rem, imm);
    }

    public static class MojProducer implements IFractalProducer {
        Complex[] complexes;
        public MojProducer(List<Complex> complexes) {
            this.complexes = new Complex[complexes.size()];
            for (int i = 0; i < complexes.size(); i++)
                this.complexes[i] = complexes.get(i);
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer,
                            AtomicBoolean cancel) {

            System.out.println("Zapocinjem izracun...");

            short[] data = new short[width * height];


            ComplexRootedPolynomial polynomial =
                    new ComplexRootedPolynomial(Complex.ONE, complexes);

            calculate(reMin, reMax, imMin, imMax, width, height, 0, height-1, data, cancel,
                        polynomial, 0.001, 0.001);

            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short)(polynomial.toComplexPolynom().order()+1), requestNo);

        }

    }

    public static void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height,
                                  int ymin, int ymax, short[] data, AtomicBoolean cancel,
                                  ComplexRootedPolynomial polynomial, double rootTreshold,
                                  double convergenceTreshold) {

        int offset = ymin * width;
        for(int y = ymin; y <= ymax && !cancel.get(); ++y) {
            for (int x = 0; x < width; ++x) {

                double cre = x / (width-1.0) * (reMax - reMin) + reMin;
                double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;

                Complex zn =  new Complex(cre, cim);
                double module = 0.0;
                int iters = 0, maxIter = 500;
                ComplexPolynomial derivate = polynomial.toComplexPolynom().derive();
                do {
                    Complex numerator = polynomial.apply(zn);
                    Complex denominator = derivate.apply(zn);
                    Complex znold = zn;
                    Complex fraction = numerator.divide(denominator);
                    zn = zn.sub(fraction);
                    module = znold.sub(zn).module();
                    iters++;
                } while (iters < maxIter && module > convergenceTreshold);
                data[offset++] = (short)(polynomial.indexOfClosestRootFor(zn, rootTreshold)+1);
            }
        }
    }
}
