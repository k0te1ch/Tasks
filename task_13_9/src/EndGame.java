import util.JTableUtils;

import javax.swing.*;
import java.awt.event.*;

public class EndGame extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelScore;
    private JLabel labelTime;
    private JLabel labelRecord;

    public EndGame(Game game) {
        this.setTitle("Игра окончена");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.labelScore.setText("Ваш счёт: "+game.getScore());
        this.labelTime.setText("Ваше время: "+game.getTime());
        this.labelRecord.setText("Ваш рекорд: "+game.getRecord());


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        buttonCancel.addActionListener(e -> {
            onCancel();
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonOK.addActionListener(e -> {
            game.restartGame();
            this.setVisible(false);
        });
    }
    private void onCancel() {
        System.exit(0);
    }
}
