import javafx.util.Pair;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Knn {



    public static String Knn(int k, Parameters param, List<Flower> listaKwiat) {
        ArrayList<Pair<Double, String>> aList = new ArrayList<>();

        for (Flower f : listaKwiat) {
            double odl = f.paramtery.distance(param);
            aList.add(new Pair<>(odl, f.nazwa));


        }

        Map<String, Integer> mapa = new TreeMap();

        Collections.sort(aList, new Comparator<Pair<Double, String>>() {
            @Override
            public int compare(Pair<Double, String> p1, Pair<Double, String> p2) {
                if (p1.getKey() < p2.getKey())
                    return -1; //elem przed
                else if (p1.getKey() == p2.getKey()) {
                    return 0;

                } else return 1;
            }
        });

        for (int i = 0; i < k; i++) { //!!!!

            mapa.putIfAbsent(aList.get(i).getValue(), 1);
            mapa.compute(aList.get(i).getValue(), (key, value) -> value + 1);

        }

        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();

    }

    public static Parameters getParameters(String[] daneinfo){

        ArrayList<Double>  parametry= new ArrayList<>();


        for (int i =0; i <daneinfo.length-1;i++){
            parametry.add(Double.parseDouble(daneinfo[i]));

        }

        return new Parameters(parametry);

    }


    public static List<Flower> getDane(Path pat){
        List<Flower> flowerList = new ArrayList<>();


        try {
            Scanner scanner = new Scanner(pat);
            String[] daneinfo;
            while(scanner.hasNext()){
                daneinfo = scanner.next().split(";");
                String flowerName=daneinfo[daneinfo.length-1];
                flowerList.add(new Flower(flowerName,getParameters(Arrays.copyOf(daneinfo,daneinfo.length-1))));
            }

        }catch(Exception e){
            e.getStackTrace();
        }

        return flowerList;

    }

    public static void  Test(Path pat,int k, List<Flower> flist){
        List<Parameters> paramList = new ArrayList<>();
        int row=0;
        int rowOK=0;

        try {
            Scanner scanner = new Scanner(pat);
            String[] daneinfo;
            while(scanner.hasNext()){
                daneinfo = scanner.next().split(";");
                String flowerName=daneinfo[daneinfo.length-1];
                Parameters pp = getParameters((Arrays.copyOf(daneinfo,daneinfo.length-1)));

                String resKnn = Knn(k,pp,flist);
                System.out.println(resKnn);
                if(resKnn.equals(flowerName)){
                    rowOK++;
                }
                row++;

            }


        }catch(Exception e){
            e.getStackTrace();
        }
        System.out.println("Accurency: "+ (float)rowOK/row*100);
        //System.out.print((float)rowOK/row*100);


    }
//C:\Users\Andrzej\Desktop\SKJ_s18793\SKJ_s18793\s18793\Knn



    public static void main(String[] args) {


        //-----------------------------
        //Proszę o ustawienie falgi w zależności od tego w jaki sposob chcą panstwo wprowadzić scieżke plikow.
        //-----------------------------
        boolean kk= false; //flaga
        Scanner lf = new Scanner(System.in);

        String path1="C:\\Users\\Andrzej\\Desktop\\SKJ_s18793\\SKJ_s18793\\s18793\\Knn\\irisTrening.csv"; //sciezka do pliku treningowego
        String path2  ="C:\\Users\\Andrzej\\Desktop\\SKJ_s18793\\SKJ_s18793\\s18793\\Knn\\irisTest.csv"; //sciezka do pliku testowego

        int k = 5; // nasze K(najblizszych)


        if(kk) {


            System.out.println("podaj sciezke do pliku treingowego: ");
            path1 = lf.nextLine();

            System.out.println("podaj sciezke do pliku testowego: ");

            path2 = lf.nextLine();

            System.out.println("podaj k: ");

            k = lf.nextInt();

        }
        List<Flower> listFlower = getDane(Paths.get(path1));
        Test(Paths.get(path2), k, listFlower);

        System.out.println(" ");
        System.out.println("Podaj własne parametry, do kwalifikacji (po spacjach): ");

        boolean bool = true;

        while (bool){
            String s = lf.nextLine();

            System.out.println(Knn(k, getParameters(s.split(" ")), listFlower));


        }

    }



}