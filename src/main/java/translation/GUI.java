package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;
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
            countryPanel.add(scrollPane);



            languageComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String selectedLanguage = languageComboBox.getSelectedItem().toString();
                        String country = list.getSelectedValue().toString();
                        System.out.println("Selected Language: " + selectedLanguage);


                        String countryCode = countryCodeConverter.fromCountry(country);
                        String languageCode = languageCodeConverter.fromLanguage(selectedLanguage);
                        System.out.println("language code: " + languageCode);
                        System.out.println("country code: " + countryCode);
                        String result = translator.translate(countryCode.toLowerCase(), languageCode);
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
                        if (!e.getValueIsAdjusting()) {
                            String selected = list.getSelectedValue();
                            String language = languageComboBox.getSelectedItem().toString();
                            System.out.println("Selected Country: " + selected);


                            String countryCode = countryCodeConverter.fromCountry(selected);
                            String languageCode = languageCodeConverter.fromLanguage(language);
                            System.out.println("language code: " + languageCode);
                            System.out.println("country code: " + countryCode);
                            String result = translator.translate(countryCode.toLowerCase(), languageCode);
                            if (result == null) {
                                result = "no translation found!";
                            }
                            resultLabel.setText(result);}

                    }
                });



            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(countryPanel);

//            mainPanel.setLayout(new BorderLayout()); // Use BorderLayout for the main panel
//
//            mainPanel.add(languagePanel, BorderLayout.NORTH); // Place languagePanel at the top
//            mainPanel.add(buttonPanel, BorderLayout.CENTER); // Place the scrollPane (JList) in the center
//            mainPanel.add(countryPanel, BorderLayout.SOUTH); // Place buttonPanel at the bottom


            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
