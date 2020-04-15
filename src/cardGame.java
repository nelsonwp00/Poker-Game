import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class stores all required Swing components and methods for a simple card game.
 *
 * @author Pang Chun Ho Nelson
 * @version 1.0
 */
public class cardGame {

    private static JFrame frame = new JFrame();
    private static JPanel MainPanel = new JPanel();
    private static JPanel UserPanel = new JPanel();
    private static JPanel DealerPanel = new JPanel();
    private static JPanel PlayerPanel = new JPanel();
    private static JPanel RpCardBtnPanel = new JPanel();
    private static JPanel ButtonPanel = new JPanel();
    private static JPanel InfoPanel = new JPanel();
    private static JMenuBar menuBar = new JMenuBar();
    private static JMenu menu1 = new JMenu("Control");
    private static JMenu menu2 = new JMenu("Help");
    private static JMenuItem exit = new JMenuItem("Exit");
    private static JMenuItem rule = new JMenuItem("Instruction");

    private static JLabel label_Image1 = new JLabel();
    private static JLabel label_Image2 = new JLabel();
    private static JLabel label_Image3 = new JLabel();
    private static JLabel label_Image4 = new JLabel();
    private static JLabel label_Image5 = new JLabel();
    private static JLabel label_Image6 = new JLabel();
    private static ImageIcon image1 = new ImageIcon("./images/card_back.gif");

    private static JButton btn_rpcard1 = new JButton("Replace Card 1");
    private static JButton btn_rpcard2 = new JButton("Replace Card 2");
    private static JButton btn_rpcard3 = new JButton("Replace Card 3");
    private static JButton btn_start = new JButton("Start");
    private static JButton btn_result = new JButton("Result");

    private static JLabel label_bet = new JLabel("Bet: $");
    private static JLabel label_current_info = new JLabel("Please place your bet! ");
    private static JLabel label_remain_info = new JLabel("Amount of money you have: $100");

    private static JTextField txt_inputbet = new JTextField(10);

    private static ArrayList<Card> card_deck = new ArrayList<Card>(52);
    private static int replacedCardNum = 0;

    private static boolean btn_start_disabled = false;
    private static boolean btn_result_disabled = false;
    private static boolean btn_rpcard1_disabled = false;
    private static boolean btn_rpcard2_disabled = false;
    private static boolean btn_rpcard3_disabled = false;

    /**
     * This is the main method instantiates an instance of cardGame,
     * implementing method go() and method resetCardDeck().
     */
    public static void main(String args[]){
        cardGame game = new cardGame();
        game.go();

        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 13; j++) {
                String fileName = "./images/card_" + String.valueOf(i) + String.valueOf(j) + ".gif";
                Card card = new Card(j, fileName);
                card_deck.add(card);
            }
        }

        Collections.shuffle(card_deck);

    }

    /**
     * This method adds all required Swing components into the frame.
     * and registers all ActionListeners to the Swing components.
     */
    public void go(){

        label_Image1.setIcon(image1);
        label_Image2.setIcon(image1);
        label_Image3.setIcon(image1);
        label_Image4.setIcon(image1);
        label_Image5.setIcon(image1);
        label_Image6.setIcon(image1);

        DealerPanel.add(label_Image1);
        DealerPanel.add(label_Image2);
        DealerPanel.add(label_Image3);

        PlayerPanel.add(label_Image4);
        PlayerPanel.add(label_Image5);
        PlayerPanel.add(label_Image6);

        //RpCardBtnPanel.setLayout(new GridLayout(1,3));
        RpCardBtnPanel.add(btn_rpcard1);
        RpCardBtnPanel.add(btn_rpcard2);
        RpCardBtnPanel.add(btn_rpcard3);

        ButtonPanel.add(label_bet);
        ButtonPanel.add(txt_inputbet);
        ButtonPanel.add(btn_start);
        ButtonPanel.add(btn_result);

        InfoPanel.add(label_current_info);
        InfoPanel.add(label_remain_info);

        menu1.add(exit);
        menu2.add(rule);
        menuBar.add(menu1);
        menuBar.add(menu2);
        frame.setJMenuBar(menuBar);

        UserPanel.setLayout(new GridLayout(3,1));
        UserPanel.add(RpCardBtnPanel);
        UserPanel.add(ButtonPanel);
        UserPanel.add(InfoPanel);

        MainPanel.setLayout(new GridLayout(3,1));
        MainPanel.add(DealerPanel);
        MainPanel.add(PlayerPanel);
        MainPanel.add(UserPanel);
        frame.add(MainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(MainPanel);
        frame.setTitle("A Simple Card Game");
        frame.setSize(400, 450);
        frame.setVisible(true);

        btn_start.addActionListener(new startBtnListener());
        btn_result.addActionListener(new resultBtnListener());
        btn_rpcard1.addActionListener(new replaceBtnListener());
        btn_rpcard2.addActionListener(new replaceBtnListener());
        btn_rpcard3.addActionListener(new replaceBtnListener());
        exit.addActionListener(new exitMenuListener());
        rule.addActionListener(new ruleMenuListener());

    }

    /**
     * This method replaces a new card image of a JLabel.
     * @param label_image a JLabel storing a card image.
     */
    static void replaceCard(JLabel label_image){
        String filename = card_deck.get(replacedCardNum).getFilename();
        ImageIcon image = new ImageIcon(filename);
        label_image.setIcon(image);
        replacedCardNum++;
    }

    /**
     * This method shuffles the cards in the card deck.
     * @param card_deck an ArrayList of type <Card> storing 52 cards.
     */
    static void resetCardDeck(ArrayList<Card> card_deck){
        Collections.shuffle(card_deck);
        replacedCardNum = 0;
    }

    /**
     * This method enables or disables all buttons.
     * @param status if status is true, then disables all buttons and vice versa.
     */
    static void setAllBtn(boolean status){
        btn_rpcard1_disabled = status;
        btn_rpcard2_disabled = status;
        btn_rpcard3_disabled = status;
        btn_start_disabled = status;
        btn_result_disabled = status;
    }

    /**
     * This method changes all JLabels of storing card images into storing card back images.
     */
    static void resetAllLabel_Image(){
        label_Image1.setIcon(image1);
        label_Image2.setIcon(image1);
        label_Image3.setIcon(image1);
        label_Image4.setIcon(image1);
        label_Image5.setIcon(image1);
        label_Image6.setIcon(image1);
    }

    class startBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            String input_money = txt_inputbet.getText();
            if (input_money.isEmpty()) input_money = "0";
            double input_value = Double.valueOf(input_money);
            String current_bet = (String.valueOf((int) input_value));
            int remainMoney;
            JFrame DialogFrame = new JFrame();
            String message;

            if (!btn_start_disabled) {

                remainMoney = Integer.valueOf(label_remain_info.getText().substring(27));

                if(input_value <= 0 || input_value != (int) input_value) {
                    message = "WARNGING: The bet you place must be a positive integer!";
                    JOptionPane.showMessageDialog(DialogFrame, message);
                }
                else {
                    if (input_value > remainMoney) {
                        message = "WARNGING: You only have $" + remainMoney + "!";
                        JOptionPane.showMessageDialog(DialogFrame, message);
                    }
                    else {
                        resetCardDeck(card_deck);
                        label_current_info.setText("Your current bet is: $" + current_bet);
                        replaceCard(label_Image4);
                        replaceCard(label_Image5);
                        replaceCard(label_Image6);
                        btn_start_disabled = true;
                    }
                }
            }
        }
    }

    class resultBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            boolean playerWin = false;
            boolean gameEnded = false;
            int playerSpecialCard = 0;
            int dealerSpecialCard = 0;
            double playerPoint = 0 ;
            double dealerPoint = 0;
            String input_money = txt_inputbet.getText();
            double input_value;
            String str_remainMoney;
            int remainMoney;
            JFrame DialogFrame = new JFrame();
            String message;

            if (btn_start_disabled && !btn_result_disabled) {

                str_remainMoney = label_remain_info.getText().substring(27);
                remainMoney = Integer.valueOf(str_remainMoney);
                input_value = Double.valueOf(input_money);
                replaceCard(label_Image1);
                replaceCard(label_Image2);
                replaceCard(label_Image3);

                for (Card card : card_deck) {
                    int cardFound = 0;
                    if (cardFound == 6) break;

                    if (label_Image1.getIcon().toString() == card.getFilename()) {
                        if (card.getValue() >= 11) dealerSpecialCard++;
                        dealerPoint += card.getValue();
                        cardFound++;
                    } else if (label_Image2.getIcon().toString() == card.getFilename()) {
                        if (card.getValue() >= 11) dealerSpecialCard++;
                        dealerPoint += card.getValue();
                        cardFound++;
                    } else if (label_Image3.getIcon().toString() == card.getFilename()) {
                        if (card.getValue() >= 11) dealerSpecialCard++;
                        dealerPoint += card.getValue();
                        cardFound++;
                    } else if (label_Image4.getIcon().toString() == card.getFilename()) {
                        if (card.getValue() >= 11) playerSpecialCard++;
                        playerPoint += card.getValue();
                        cardFound++;
                    } else if (label_Image5.getIcon().toString() == card.getFilename()) {
                        if (card.getValue() >= 11) playerSpecialCard++;
                        playerPoint += card.getValue();
                        cardFound++;
                    } else if (label_Image6.getIcon().toString() == card.getFilename()) {
                        if (card.getValue() >= 11) playerSpecialCard++;
                        playerPoint += card.getValue();
                        cardFound++;
                    }
                }

                dealerPoint /= 10;
                playerPoint /= 10;
                dealerPoint = Integer.valueOf(String.valueOf(dealerPoint).substring(2));
                playerPoint = Integer.valueOf(String.valueOf(playerPoint).substring(2));

                if (playerSpecialCard > dealerSpecialCard) playerWin = true;
                else if (playerSpecialCard == dealerSpecialCard) {
                    if (playerPoint > dealerPoint) playerWin = true;
                }

                if (playerWin) {
                    message = "Congrauations! You wins this round!";
                    JOptionPane.showMessageDialog(DialogFrame, message);
                    remainMoney += input_value;
                    label_remain_info.setText("Amount of money you have: $" + remainMoney);
                    label_current_info.setText("Please place your bet! ");
                } else {
                    message = "Sorry! The Dealer wins this round!";
                    JOptionPane.showMessageDialog(DialogFrame, message);
                    remainMoney -= input_value;
                    label_remain_info.setText("Amount of money you have: $" + remainMoney);
                    label_current_info.setText("Please place your bet! ");
                }

                setAllBtn(false);

                if (remainMoney <= 0) {
                    label_current_info.setText("You have no more money!");
                    label_remain_info.setText("Please start a new game!");
                    message = "Game over!\nYou have no more money!\nPlease start a new game!";
                    JOptionPane.showMessageDialog(DialogFrame, message);
                    setAllBtn(true);
                    gameEnded = true;
                }

                if (!gameEnded) {
                    resetAllLabel_Image();
                }
            }
        }
    }

    class replaceBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (btn_start_disabled && replacedCardNum < 3+2) {
                if (e.getSource() == btn_rpcard1 && !btn_rpcard1_disabled) {
                    replaceCard(label_Image4);
                    btn_rpcard1_disabled = true;
                } else if (e.getSource() == btn_rpcard2 && !btn_rpcard2_disabled) {
                    replaceCard(label_Image5);
                    btn_rpcard2_disabled = true;
                } else if (e.getSource() == btn_rpcard3 && !btn_rpcard3_disabled) {
                    replaceCard(label_Image6);
                    btn_rpcard3_disabled = true;
                }
            }
        }
    }

    class exitMenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class ruleMenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame DialogFrame = new JFrame();
            String message;
            message = "Rules to determine who has better cards:\n" +
                      "J, Q, K are regarded as special cards.\n" +
                      "Rule 1: The one with more special cards wins.\n" +
                      "Rule 2: If both have the same number of special cards, add the face values of the other" +
                      "card(s) and take the remainder after dividing the sum by 10. The one with a bigger" +
                      "remainder wins. (Note: Ace = 1).\n" +
                      "Rule 3: The dealer wins if both rule 1 and rule 2 cannot distinguish the winner.";
            JOptionPane.showMessageDialog(DialogFrame, message);
        }
    }

}
