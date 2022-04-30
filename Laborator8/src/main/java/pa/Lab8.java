package pa;

import java.nio.charset.MalformedInputException;

class Lab8 {

    public static void main(String[] args) {
        var exe = new Lab8();


        //exe.compulsory();
        exe.homework();
    }

    public void compulsory(){
        DatabaseConnection.getInstance().constructDB();
        DatabaseConnection.getInstance().populateDB();
        System.out.println((new ContinentDAODatabase()).getAll());
        System.out.println((new CountryDAODatabase()).getAll());
        System.out.println((new CityDAODatabase()).getAll());
    }

    public void homework(){
        DatabaseConnection.getInstance().constructDB();
        DatabaseConnection.getInstance().populateDB();

        var frame = new MainFrame();
        frame.setVisible(true);
    }
}