package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CalcLayout implements LayoutManager2 {
    private int padding;
    private Map<String, Component> layouts;

    private int minWidth = 0, minHeight = 0;
    private int preferredWidth = 0, preferredHeight = 0;
    private boolean sizeUnknown = true;
    public CalcLayout(int padding) {
        this.padding=padding;
        layouts = new HashMap<>();
    }

    public CalcLayout() {
        this(0);
    }

    private String napraviKljuc(int row, int column) {
        return row + "," + column;
    }

    private void check(RCPosition position) {
        int row = position.getRow(), column = position.getColumn();
        if (row < 1 || row > 5 || column < 1 || column > 7)
            throw new CalcLayoutException("Redak mora biti izmedu 1 i 5, a stupac izmedu 1 i 7.");
        if (row == 1 && (column > 1 && column < 6))
            throw new CalcLayoutException("Prvi redak ima stupce 1, 6 i 7");
        String key = napraviKljuc(row, column);
        if (layouts.containsKey(key))
            throw new CalcLayoutException("Za svako ogranicenje maksimalno jedna komponenta.");
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        RCPosition position;
        if (constraints instanceof RCPosition) {
            position = (RCPosition) constraints;
        } else if (constraints instanceof String) {
            position = RCPosition.parse((String) constraints);
        } else
            throw new IllegalArgumentException();
        check(position);
        layouts.put(napraviKljuc(position.getRow(), position.getColumn()), comp);
    }


    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        for (String key : layouts.keySet()) {
            if (layouts.get(key).equals(comp)) {
                layouts.remove(key);
                return;
            }
        }
    }

    private int[] izracunajVelicineConteinera(int size, int totalSize) {
        int[] velicine = new int[size+1];
        totalSize = totalSize - size*padding + padding;
        int osnovnaVelicina = totalSize / size, ostatak = totalSize - osnovnaVelicina * size;
        //svi ima jednake velicine
        for (int i = 1; i <= size; i++) {
            velicine[i] = osnovnaVelicina;
        }
        //prvo nadodajemo neparnim jedinicama
        for (int i = 1; i <= size; i += 2) {
            if (ostatak == 0)
                break;
            velicine[i]++;
            ostatak--;
        }
        //onda nadodajemo nepranim elementima
        for (int i = size-1; i > 0; i -= 2) {
            if (ostatak == 0)
                break;
            velicine[i]++;
            ostatak--;
        }
        return velicine;
    }

    private Dimension genericLayoutSize(Container parent, Size size) {
        int maxWidth = 0, maxHeight = 0;
        for (String key : layouts.keySet()) {
            Dimension dimension = size.getSize(layouts.get(key));
            if (key.equals("1,1")) {
                dimension.width = (dimension.width - 4 * padding) / 5;
            }
            if (dimension == null)
                continue;
            maxWidth = Math.max(maxWidth, dimension.width);
            maxHeight = Math.max(maxHeight, dimension.height);
        }
        return new Dimension(maxWidth * 7 + 6 * padding,
                maxHeight * 5 + 4 * padding);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Size size = component -> component.getPreferredSize();
        return genericLayoutSize(parent, size);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        Size size = component -> component.getMaximumSize();
        return genericLayoutSize(target, size);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Size size = component -> component.getMinimumSize();
        return genericLayoutSize(parent, size);
    }

    private int izracunajPocetnuTocku(int[] polje, int kraj) {
        int razlika = 0;
        for (int i = 1; i < kraj; i++) {
            razlika += polje[i] + padding;
        }
        return razlika;
    }

    private int izracunajKrajnjuTocku(int[] polje, int kraj) {
        int razlika = 0;
        for (int i = 1; i <= kraj; i++) {
            razlika += polje[i] + padding;
        }
        return razlika-padding;
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int maxHeight = parent.getHeight() - (insets.top + insets.bottom);
        int maxWidth = parent.getWidth() - (insets.right + insets.left);
        int[] width = izracunajVelicineConteinera(7, maxWidth),
            height = izracunajVelicineConteinera(5, maxHeight);
        for (String key : layouts.keySet()) {
            Component component = layouts.get(key);
            String r = String.valueOf(key.charAt(0)), c = String.valueOf(key.charAt(2));
            if (component.isVisible()) {
                int x = insets.left, y = insets.top, w = insets.left, h = insets.top;
                if (key.equals("1,1")) {
                    w += izracunajKrajnjuTocku(width, 5);
                    h += izracunajKrajnjuTocku(height, 1);
                } else {
                    int row = Integer.valueOf(r), col = Integer.valueOf(c);
                    x += izracunajPocetnuTocku(width, col);
                    y += izracunajPocetnuTocku(height, row);
                    w += izracunajKrajnjuTocku(width, col);
                    h += izracunajKrajnjuTocku(height, row);
                }
                component.setBounds(x,y,w-x,h-y);
            }
        }

    }
}
