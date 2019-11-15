package generators.utils;

import java.util.List;
import java.util.Random;

public class RandomObject<T> {
    public T randomObject(List<T> objectList) {
        Random rand = new Random();
        return objectList.get(rand.nextInt(objectList.size()));
    }
}
