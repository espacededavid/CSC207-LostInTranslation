package examples;

import translation.CanadaTranslator;
import translation.Translator;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Arrays;

public class JListDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JPanel languagePanel = new JPanel();
            languagePanel.setLayout(new GridLayout(0, 2));
            languagePanel.add(new JLabel("Language:"), 0);


            Translator translator = new CanadaTranslator();

            String[] items = new String[translator.getLanguageCodes().size()];

            JComboBox<String> languageComboBox = new JComboBox<>();
            int i = 0;
            for (String langaugeCode : translator.getLanguageCodes()) {
                items[i++] = langaugeCode;
            }

            // create the JList with the array of strings and set it to allow multiple
            // items to be selected at once.
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane scrollPane = new JScrollPane(list);
            languagePanel.add(scrollPane, 1);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);


            list.addListSelectionListener(new ListSelectionListener() {

                /**
                 * Called whenever the value of the selection changes.
                 *
                 * @param e the event that characterizes the change.
                 */
                @Override
                public void valueChanged(ListSelectionEvent e) {

                    int[] indices = list.getSelectedIndices();
                    String[] items = new String[indices.length];
                    for (int i = 0; i < indices.length; i++) {
                        items[i] = list.getModel().getElementAt(indices[i]);
                    }

                    JOptionPane.showMessageDialog(null, "User selected:" +
                                                   System.lineSeparator() + Arrays.toString(items));

                }
            });

            JFrame frame = new JFrame("JList Demo");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // place in centre of screen
            frame.pack();
            frame.setVisible(true);


        });
    }
}
