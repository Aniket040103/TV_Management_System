import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainScreen extends JFrame {

    // Panel1 : user registration
    JPanel subscriberPanel;

    JTextField subName;
    JTextField subLastName;
    JTextField subMobile;
    JTextField subCity;

    JLabel nameLBL;
    JLabel lastLBL;
    JLabel mobileLBL;
    JLabel cityLBL;

    //panel2: cycle
    JTextField startCycleFLD;
    JTextField endCycleFLD;
    JTextField numberTVFLD;
    JLabel todayLBL;
    JPanel CyclePanel;
    SimpleDateFormat df;
    Date currentDate;
    JLabel startCycleLBL;
    JLabel endCycleLBL;
    JLabel numberTVLBL;

    //Panel 3 channel packages
    JCheckBox sportsCHKBX;
    JCheckBox moviesCHKBX;
    JCheckBox docCHKBX;
    JPanel packagesPanel;

    //Panel 4 : Package Details
    JTextArea channelsAreaS;
    JTextArea channelsAreaM;
    JTextArea channelsAreaD;
    JPanel detailsPanel;

    //Panel 5  : Check and payments
    JLabel installFeeLBL;
    JPanel feePanel;
    JLabel packageFeeLBL;
    JLabel totalFeeLBL;

    //Panel 6 : Table (Date of Subscription)
    JTable table;
    DefaultTableModel tableModel;
    JPanel p6Panel;

    //panel 7: Action Panel
    JButton saveBTN;
    JButton loadBTN;
    JButton newBTN;
    JPanel p7ActionPanel;

    //classes and objects
    Subscriber subscriber;
    Subscription subscription;

    int packagesSelectedPrice = 0;
    int totalPrice;

    //Saving
    ArrayList<Subscription> listToSave = new ArrayList<>();
    File file;
    public MainScreen(){
    /************** Panel 1 ************/

        subscriberPanel = new JPanel();
        Border panel1Title = BorderFactory.createTitledBorder("Subscriber Details");
        subscriberPanel.setBorder(panel1Title);
        subscriberPanel.setBounds(15,15,300,200);
        subscriberPanel.setLayout(new GridLayout(4,2));

        //JLabel
        nameLBL = new JLabel("First Name: ");
        lastLBL = new JLabel("Last Name: ");
        mobileLBL = new JLabel("Mobile: ");
        cityLBL = new JLabel("City: ");

        //JTextFields
        subName = new JTextField();
        subLastName = new JTextField();
        subMobile =  new JTextField();
        subCity = new JTextField();

        // Adding Components to Panel1
        subscriberPanel.add(nameLBL);
        subscriberPanel.add(subName);
        subscriberPanel.add(lastLBL);
        subscriberPanel.add(subLastName);
        subscriberPanel.add(mobileLBL);
        subscriberPanel.add(subMobile);
        subscriberPanel.add(cityLBL);
        subscriberPanel.add(subCity);
        /*************** Panel 2 **************/

        CyclePanel = new JPanel();
        CyclePanel.setBounds(15,230,300,500);
        CyclePanel.setLayout(new GridLayout(14,1));
        Border CycleBorder = BorderFactory.createTitledBorder("Cycle Details");
        CyclePanel.setBorder(CycleBorder);

        // Components of cycle Panel
        todayLBL = new JLabel();
        df = new SimpleDateFormat("dd/mm/yyyy");
        currentDate = new Date();
        todayLBL.setText("Today: "+df.format(currentDate));

        //Start cycle date
        startCycleLBL = new JLabel("Start Cycle Date (DD/MM/YYYY):");
        startCycleFLD = new JTextField();

        //End Cycle date
        endCycleLBL = new JLabel("End Cycle Date (DD/MM/YYYY):");
        endCycleFLD = new JTextField();

        //Number Of Tvs
        numberTVLBL = new JLabel("Number of Tv:");
        numberTVFLD = new JTextField();

        CyclePanel.add(todayLBL);
        CyclePanel.add(startCycleLBL);
        CyclePanel.add(startCycleFLD);
        CyclePanel.add(endCycleLBL);
        CyclePanel.add(endCycleFLD);
        CyclePanel.add(numberTVLBL);
        CyclePanel.add(numberTVFLD);

        // make opacity for fields
        startCycleFLD.setOpaque(false);
        endCycleFLD.setOpaque(false);
        numberTVFLD.setOpaque(false);

        /*************** Panel 3 **************/

        packagesPanel = new JPanel();
        packagesPanel.setBounds(330,15,300,200);
        packagesPanel.setLayout(new GridLayout(5,1));
        Border PackBorder = BorderFactory.createTitledBorder("Available Packages");
        packagesPanel.setBorder(PackBorder);

        JLabel packagesLBL = new JLabel("Please select your packages");
        sportsCHKBX = new JCheckBox("Sports Package");
        moviesCHKBX = new JCheckBox("Movies Package");
        docCHKBX = new JCheckBox("Documentary Package");

        JButton subscribeBTN = new JButton("Subscribe");
        packagesPanel.add(packagesLBL);
        packagesPanel.add(sportsCHKBX);
        packagesPanel.add(moviesCHKBX);
        packagesPanel.add(docCHKBX);
        packagesPanel.add(subscribeBTN);

        // Checkbox Item Listeners
        sportsCHKBX.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (sportsCHKBX.isSelected()){
                    DisplaySportsChannels();
                    //make price changes
                }else{
                    channelsAreaS.setText("");
                }
            }
        });
        moviesCHKBX.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (moviesCHKBX.isSelected()){
                    DisplayMoviesChannels();
                }else{
                    channelsAreaM.setText("");
                }
            }
        });
        docCHKBX.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (docCHKBX.isSelected()){
                    DisplayDocChannels();
                }else{
                    channelsAreaD.setText("");
                }
            }
        });
        subscribeBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    GetSubscriberData();
                }catch (Exception ee){

                }
            }
        });
        /*************** Panel 4 **************/

        detailsPanel = new JPanel();
        detailsPanel.setBounds(330,230,300,500);
        detailsPanel.setLayout(new GridLayout(3,1));
        Border p4Border = BorderFactory.createTitledBorder("Available Channels");
        detailsPanel.setBorder(p4Border);

        channelsAreaS = new JTextArea(5,1);
        channelsAreaS.setEditable(false);
        channelsAreaS.setOpaque(false);
        channelsAreaS.setLineWrap(true);

        channelsAreaM = new JTextArea(5,1);
        channelsAreaM.setEditable(false);
        channelsAreaM.setOpaque(false);
        channelsAreaM.setLineWrap(true);

        channelsAreaD = new JTextArea(5,1);
        channelsAreaD.setEditable(false);
        channelsAreaD.setOpaque(false);
        channelsAreaD.setLineWrap(true);

        detailsPanel.add(channelsAreaS);
        detailsPanel.add(channelsAreaM);
        detailsPanel.add(channelsAreaD);

        /************** Panel 5 ************/

        feePanel = new JPanel();
        Border blackLine = BorderFactory.createTitledBorder("Fee & Check");
        feePanel.setBorder(blackLine);
        feePanel.setBounds(645,15,200,200);
        feePanel.setLayout(new GridLayout(3,1));

        installFeeLBL = new JLabel("Installation Fee: ");
        packageFeeLBL = new JLabel("Packages Fee: ");
        totalFeeLBL = new JLabel("Total Amount to Pay: ");
        feePanel.add(installFeeLBL);
        feePanel.add(packageFeeLBL);
        feePanel.add(totalFeeLBL);

        /*************** Panel 6 **************/
        p6Panel = new JPanel();
        Border border6 = BorderFactory.createTitledBorder("Our Customers");
        p6Panel.setBorder(border6);
        p6Panel.setBounds(645,230,515,500);
        p6Panel.setLayout(new GridLayout(3,1));

        //Table:
        // 1 - tabel model
        tableModel = new DefaultTableModel();

        // 2 - colums
        table = new JTable(tableModel);
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Start Cycle");
        tableModel.addColumn("End Cycle");
        tableModel.addColumn("Total Fee");

        //3 - Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        p6Panel.add(scrollPane);

        /*************** Panel 7 **************/
        p7ActionPanel = new JPanel();
        Border border7 = BorderFactory.createTitledBorder("Action Tab");
        p7ActionPanel.setBorder(border7);
        p7ActionPanel.setBounds(860,15,300,200);
        p7ActionPanel.setLayout(new GridLayout(4,1));

        saveBTN = new JButton("Save Subscription");
        loadBTN = new JButton("Load Subscription");
        newBTN = new JButton("New Subscription");

        p7ActionPanel.add(newBTN);
        p7ActionPanel.add(saveBTN);
        p7ActionPanel.add(loadBTN);

        saveBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveSubscriptionToDisk();
            }
        });
        newBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewSubscription();
            }
        });
        loadBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               ArrayList<Subscription> k = LoadDataFromDisk();
            }
        });



        //Adding panels to JFrame
        setLayout(null);            // null layout for JFrame
        add(subscriberPanel);       //panel 1
        add(CyclePanel);            //panel 2
        add(packagesPanel);         //panel 3
        add(detailsPanel);          //panel 4
        add(feePanel);              ///panel 5
        add(p6Panel);
        add(p7ActionPanel);


    }
    private int DisplaySportsChannels(){
        SportsChannel m1 = new SportsChannel("MBC Bundle","En","MOV",4);
        SportsChannel m2 = new SportsChannel("Cinema One","EN","MOV",5);
        SportsChannel m3 = new SportsChannel("Cinema Pro","Ru","MOV",6);
        SportsChannel m4 = new SportsChannel("Cinema 1","SP","MOV",2);
        SportsChannel m5 = new SportsChannel("Movie Home","GR","MOV",4);

        ArrayList<SportsChannel> sportsChannels = new ArrayList<>();
        sportsChannels.add(m1);
        sportsChannels.add(m2);
        sportsChannels.add(m3);
        sportsChannels.add(m4);
        sportsChannels.add(m5);

        String sprtChannelString = "";
        int packagePrice = 0;
        for (int i=0; i< sportsChannels.size();i++){
            sprtChannelString +=
                    "       "+sportsChannels.get(i).getChannelName()
                            +"       "+ sportsChannels.get(i).getLanguage()
                            +"       "+ sportsChannels.get(i).getPrice()
                            +"\n";
            packagePrice+=sportsChannels.get(i).getPrice();
        }
        channelsAreaS.setText(sprtChannelString);
        return packagePrice;
    }
    private int DisplayMoviesChannels(){
        MoviesChannel m1 = new MoviesChannel("MBC Bundle","En","MOV",4);
        MoviesChannel m2 = new MoviesChannel("Cinema One","EN","MOV",5);
        MoviesChannel m3 = new MoviesChannel("Cinema Pro","Ru","MOV",6);
        MoviesChannel m4 = new MoviesChannel("Cinema 1","SP","MOV",2);
        MoviesChannel m5 = new MoviesChannel("Movie Home","GR","MOV",4);

        ArrayList<MoviesChannel> moviesChannels = new ArrayList<>();
        moviesChannels.add(m1);
        moviesChannels.add(m2);
        moviesChannels.add(m3);
        moviesChannels.add(m4);
        moviesChannels.add(m5);

        String movChannelString = "";
        int packagePrice = 0;
        for (int i=0; i< moviesChannels.size();i++){
            movChannelString +=
                    "       "+moviesChannels.get(i).getChannelName()
                            +"       "+ moviesChannels.get(i).getLanguage()
                            +"       "+ moviesChannels.get(i).getPrice()
                            +"\n";
            packagePrice+=moviesChannels.get(i).getPrice();
        }
        channelsAreaM.setText(movChannelString);
        return packagePrice;
    }
    private int DisplayDocChannels(){
        DocumentryChannel m1 = new DocumentryChannel("NAT GEO","SP","DOC",3);
        DocumentryChannel m2 = new DocumentryChannel("PBC America","EN","DOC",2);
        DocumentryChannel m3 = new DocumentryChannel("World Documentry","EN","DOC",3);
        DocumentryChannel m4 = new DocumentryChannel("Canal D","SP","DOC",4);
        DocumentryChannel m5 = new DocumentryChannel("Discovery","GR","DOC",5);

        ArrayList<DocumentryChannel> documentryChannels = new ArrayList<>();
        documentryChannels.add(m1);
        documentryChannels.add(m2);
        documentryChannels.add(m3);
        documentryChannels.add(m4);
        documentryChannels.add(m5);

        String docChannelString = "";
        int packagePrice = 0;
        for (int i=0; i< documentryChannels.size();i++){
            docChannelString +=
                     "       "+documentryChannels.get(i).getChannelName()
                    +"       "+ documentryChannels.get(i).getLanguage()
                    +"       "+ documentryChannels.get(i).getPrice()
                    +"\n";
            packagePrice+=documentryChannels.get(i).getPrice();
        }
        channelsAreaD.setText(docChannelString);

        return packagePrice;
    }
    private void GetSubscriberData() throws ParseException {
         Date currentDate = new Date();

         //Subscriber Data
        subscriber = new Subscriber(
                subName.getText(),
                subLastName.getText(),
                subCity.getText(),
                Integer.parseInt(subMobile.getText())
        );

        //cycle
        Date startCycle = df.parse(startCycleFLD.getText());
        Date endCycle = df.parse(endCycleFLD.getText());

        SybscriptionCycle cycle = new SybscriptionCycle(
                df.format(startCycle),
                df.format(endCycle)
        );

        // Subscrition
        subscription = new Subscription(
                Integer.parseInt(numberTVFLD.getText()),
                subscriber,
                cycle,
                df.format(currentDate)
        );

        installFeeLBL.setText("Installation Fee:"+
                subscription.getTotalFee()+"$");

        ShowPrice();

    }
    private void ShowPrice(){

        if (docCHKBX.isSelected()) {
            packagesSelectedPrice += DisplayDocChannels();
        }
        else if (moviesCHKBX.isSelected()){
            packagesSelectedPrice+= DisplayMoviesChannels();
        }
        else if (sportsCHKBX.isSelected()){
            packagesSelectedPrice+= DisplaySportsChannels();
        }

        packageFeeLBL.setText("Packages Fee: "+packagesSelectedPrice +" $");
        totalPrice = subscription.getTotalFee()+packagesSelectedPrice;
        totalFeeLBL.setText("Total Amount To Pay: "+totalPrice+"$");
    }
    private void SaveSubscriptionToDisk(){

        listToSave.add(subscription);
        file = new File("D:/myfile.dat");
        try{
            OutputStream os = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(listToSave);
            oos.flush();
            oos.close();
            os.close();

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void NewSubscription(){
        // All Fields are empty
        subName.setText("");
        subLastName.setText("");
        subCity.setText("");
        subMobile.setText("");

        startCycleFLD.setText("");
        endCycleFLD.setText("");
        numberTVFLD.setText("");
        installFeeLBL.setText("Installation Fee: ");
        packageFeeLBL.setText("Packages Fee: ");
        totalFeeLBL.setText("Total Amount to Pay: ");

        moviesCHKBX.setSelected(false);
        sportsCHKBX.setSelected(false);
        docCHKBX.setSelected(false);

    }
    private ArrayList<Subscription> LoadDataFromDisk(){
        ArrayList<Subscription> s = new ArrayList<>();
        file = new File("D:/myfile.dat");

        try{
            InputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);
            s= (ArrayList) ois.readObject();
            ois.close();
            is.close();


        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        for (Subscription sub: s){
            DisplaySubcriptionIntable(sub);
        }
        return s;
    }
    private void DisplaySubcriptionIntable(Subscription sub){
        // Displaying data into table
        tableModel.addRow(new Object[]{
                sub.getSubscriber().getfName(),
                sub.getSubscriber().getlName(),
                sub.getSubscriber().getPhone(),
                sub.getCycle().getStartDate(),
                sub.getCycle().getEndDate(),
                sub.getTotalFee()
        });
    }
    public static void main(String[] args){
        MainScreen mainScreen = new MainScreen();
        mainScreen.setVisible(true);
        mainScreen.setBounds(20,10,1200,800);
    }
}
