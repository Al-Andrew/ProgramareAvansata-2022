package pa;

public interface Storage {
    int getStorageCapacity();

    default long getStorageIn(StorageUnit unit){
        int inGB = getStorageCapacity();

        switch (unit){
            case GIGABYTES -> {return inGB;}
            case MEGABYTES -> {return (long)inGB * 1024;}
            case KILOBYTES -> {return (long)inGB * 1048576;}
            case BYTES -> {return (long)inGB * 1073741824;}
        }
        return -1; // if we get here something bad happened. is there an unreachable!() in java?
    }
}
