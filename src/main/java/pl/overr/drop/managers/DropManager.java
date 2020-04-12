package pl.overr.drop.managers;

import pl.overr.drop.drop.Drop;

import java.util.HashSet;
import java.util.Set;

public class DropManager {

    public Set<Drop> getDropSet() {
        return dropSet;
    }

    private final Set<Drop> dropSet;

    public DropManager() {
        this.dropSet = new HashSet<>();
    }

    public void addToDrop(Drop drop){
        dropSet.add(drop);
    }
}
