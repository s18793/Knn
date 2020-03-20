import java.util.ArrayList;
import java.util.List;

public class Parameters {

    ArrayList<Double> parameters;


    public Parameters(ArrayList<Double> parameters){
        this.parameters=parameters;
    }

    public double distance(Parameters par){

        double wynik=0.0;


        for(int i= 0; i<par.parameters.size(); i++){

            double x = par.parameters.get(i)-this.parameters.get(i); //atrybut z tej klasy
            wynik=x*x;
        }
        return wynik;
    }

}