package pa;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import pa.db.JPAManager;
import pa.db.Maker;
import pa.entity.City;
import pa.entity.Continent;
import pa.entity.Country;
import pa.graphics.MainFrame;
import pa.repository.JPACityRepository;
import pa.repository.JPAContinentRepository;
import pa.repository.JPACountryRepository;
import pa.repository.Repository;

import javax.persistence.EntityManager;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        var exe = new Main();

        //exe.compulsory();
        //exe.timedCityPopulate();
        //exe.homework();
        exe.gui();
    }

    public void compulsory() {
        EntityManager em = JPAManager.the().createEntityManager();

        List c = em.createQuery("select e from Continent e")
                                   .getResultList();
        System.out.println(c);
        em.close();
    }

    public void timedCityPopulate() {
        Maker.the().constructDB();
        Maker.the().populateDB(JPAContinentRepository.class, JPACountryRepository.class, JPACityRepository.class);

        long start = System.currentTimeMillis();
        EntityManager em = JPAManager.the().createEntityManager();
        em.getTransaction().begin();
        //We remove the cities so we don't have to deal with keeping up the unique constraints and updating where necessary
        for(var cty : em.createNamedQuery("City.getAll").getResultList()) {
            em.remove(cty);
        }
        em.getTransaction().commit();

        CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();

        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader("worldcities.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String[]> data = null;
        try {
             data = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .withCSVParser(parser)
                    .build()
                    .readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Repository<Country> countries = new JPACountryRepository();
        //City(String name, Country country, Long isCapital, float latitude, float longitude, Long population)
        //"city","city_ascii","lat","lng","country","iso2","iso3","admin_name","capital","population","id"
        //"Tokyo","Tokyo","35.6839","139.7744","Japan","JP","JPN","Tōkyō","primary","39105000","1392685764"
        em.getTransaction().begin();
        for(var cty_entry : data) {

            Long population = 0l;
            try { //NOTE(AlAndrew): This is here because of problems in the data-set
                population = Long.parseLong(cty_entry[9]);
            } catch (NumberFormatException ex) {
                try {
                    population = Long.parseLong(cty_entry[10]);
                } catch (NumberFormatException ex2) {
                    population = null; // If it fails twice i'm not going to bother...
                }
            }
            City constructed = new City(cty_entry[0], countries.findByName(cty_entry[4]), cty_entry[8].equals("primary")?1l:0l,
                    Float.parseFloat(cty_entry[2]), Float.parseFloat(cty_entry[3]), population);
            em.persist(constructed);
        }
        em.getTransaction().commit();
        em.close();

        long end = System.currentTimeMillis();

        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");

    }

    public void homework() {
        Repository<Continent> repo = new JPAContinentRepository();
        Continent c = repo.findByName("Futhark");
        repo.delete(c);
        System.out.println(repo.getAll());
    }

    public void gui() {
        var frame = new MainFrame();
        frame.setVisible(true);
    }
}
