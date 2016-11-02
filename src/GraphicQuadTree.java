import javax.swing.*;
import java.awt.*;

class GraphicQuadTree<E extends Boolean> extends QuadTree<E> {
    private static Color
            TRUE_COLOR = Color.BLACK,
            FALSE_COLOR = Color.WHITE;

    private JPanel panel;
    private GridBagConstraints c;

    public GraphicQuadTree(E[][] data) {
        super(data);
        panel = new JPanel(new GridBagLayout());

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        buildSquares();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void buildSquares() {
        buildSquares(getRoot(), size, 0, 0);
    }

    private void buildSquares(QuadTreeNode<E> node, int size,
                              int fromRowIndex, int fromColumnIndex) {
        if (node.isLeaf()) {
            c.gridx = fromColumnIndex;
            c.gridy = fromRowIndex;
            c.gridwidth = size;
            c.gridheight = size;
            c.weightx = size;
            c.weighty = size;

            panel.add(makeSquare(node.getData()), c);
        } else {
            int subSquareSize = size/2;
            QuadTreeNode<E>[] children = node.getChildren();
            buildSquares(children[0], subSquareSize, fromRowIndex, fromColumnIndex);
            buildSquares(children[1], subSquareSize, fromRowIndex, fromColumnIndex+subSquareSize);
            buildSquares(children[2], subSquareSize, fromRowIndex+subSquareSize, fromColumnIndex+subSquareSize);
            buildSquares(children[3], subSquareSize, fromRowIndex+subSquareSize, fromColumnIndex);
        }
    }

    private JPanel makeSquare(boolean val) {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
        result.setBackground(val ? TRUE_COLOR : FALSE_COLOR);
        result.setBorder(BorderFactory.createLineBorder(val ? FALSE_COLOR : TRUE_COLOR));
        return result;
    }
}