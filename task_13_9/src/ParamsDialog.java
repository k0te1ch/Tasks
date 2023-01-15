import util.JTableUtils;

import javax.swing.*;
import java.awt.event.ActionListener;


public class ParamsDialog extends JDialog {
    private JPanel panelMain;
    private JSlider sliderCellSize;
    private JButton buttonCancel;
    private JButton buttonOk;


    private final JTable gameFieldJTable;

    private int oldCellSize;


    public ParamsDialog(GameParams params, JTable gameFieldJTable) {
        this.setTitle("Параметры");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.gameFieldJTable = gameFieldJTable;

        this.oldCellSize = gameFieldJTable.getRowHeight();
        sliderCellSize.addChangeListener(e -> {
            int value = sliderCellSize.getValue();
            JTableUtils.resizeJTableCells(gameFieldJTable, value, value);
        });
        buttonCancel.addActionListener(e -> {
            JTableUtils.resizeJTableCells(gameFieldJTable, oldCellSize, oldCellSize);
            this.setVisible(false);
        });
        buttonOk.addActionListener(e -> {
            oldCellSize = gameFieldJTable.getRowHeight();
            this.setVisible(false);
        });
    }

    public void updateView() {
        sliderCellSize.setValue(gameFieldJTable.getRowHeight());
    }
}
