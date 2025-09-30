package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.event.ListSelectionListener;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();



            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(languageCodeConverter.fromLanguageCode(languageCode));
            }
            languagePanel.add(languageComboBox);



            JPanel buttonPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);



            JPanel countryPanel = new JPanel();
            String[] items = new String[translator.getCountryCodes().size()];
            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                items[i++] = countryCodeConverter.fromCountryCode(countryCode);
            }
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane scrollPane = new JScrollPane(list);
            languagePanel.add(scrollPane, 1);



            languageComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String selectedLanguage = languageComboBox.getSelectedItem().toString();
                        String country = list.getSelectedValue().toString();
                        System.out.println("Selected Language: " + selectedLanguage);


                        String countryCode = countryCodeConverter.fromCountry(country);
                        String languageCode = languageCodeConverter.fromLanguage(selectedLanguage);
                        String result = translator.translate(countryCode, languageCode);
                        if (result == null) {
                            result = "no translation found!";
                        }
                        resultLabel.setText(result);
                    }
                }
            });


            list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        String selected = list.getSelectedValue();
                        String language = languageComboBox.getSelectedItem().toString();
                        System.out.println("Selected Country: " + selected);


                        String countryCode = countryCodeConverter.fromCountry(selected);
                        String languageCode = languageCodeConverter.fromLanguage(language);
                        String result = translator.translate(countryCode, languageCode);
                        if (result == null) {
                            result = "no translation found!";
                        }
                        resultLabel.setText(result);
                    }
                });



            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(countryPanel);


            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
