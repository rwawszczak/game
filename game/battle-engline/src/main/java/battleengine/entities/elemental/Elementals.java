package battleengine.entities.elemental;

import battleengine.entities.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by RaV on 10.05.15.
 */
public class Elementals {
    private final List<Elemental> elementals;

    public Elementals() {
        elementals = new ArrayList<Elemental>();
    }

    public Elementals(Elemental... elemental) {
        elementals = Arrays.asList(elemental);
    }

    public Elemental get(int index){
        if(index<0||index>= elementals.size()) throw new CantFindElementalException(index);
        return elementals.get(index);
    }

    public Elemental get(String name){
        for(Elemental e : elementals){
            if(name.equals(e.getName()))return e;
        }
        throw new CantFindElementalException(name);
    }

    public int getCount(){
        return elementals.size();
    }

    public int getCount(Element type){
        int count = 0;
        for (Elemental elemental : elementals){
            if(elemental.getType() == type)count++;
        }
        return count;
    }

    public void add(Elemental elemental) {
        elementals.add(elemental);
    }

    public static class CantFindElementalException extends RuntimeException{
        public CantFindElementalException(String name) {
            super(String.format("Can't find elemental with name: \"%s\"",name));
        }
        public CantFindElementalException(int index) {
            super(String.format("Can't find elemental with index number: \"%d\"",index));
        }
    }
}
