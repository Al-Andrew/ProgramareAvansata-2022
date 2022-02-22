package lab1;

public class Lab1 {

    public static void main(String[] args) {
        Lab1 lab = new Lab1();

        lab.compulsory();
    }

    public void compulsory() {
        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);
        int result = ((n * 3) + 0b10101 + 0xFF) * 6;
        result = (result % 9 == 0) ? 9 : result % 9; // "cifra de control". Vezi https://www.pbinfo.ro/articole/13257/cifra-de-control-a-unui-numar

        System.out.println("Willy-nilly, this semester I will learn " + languages[result]);
    }


}
