package test;

import java.io.File;
import java.io.IOException;
import java.util.ServiceLoader;

public class ServantTest {
    public static void main(String[] args) throws IOException {
        ServiceLoader<IPersonalServant> servantLoader = ServiceLoader.load(IPersonalServant.class);

        IPersonalServant i = null;
        for (IPersonalServant ii : servantLoader) {
            if (ii.can("fetch tea")) {
                i = ii;
            }
        }

        if (i == null) {
            throw new IllegalArgumentException("No suitable servant found");
        }

        for (String arg : args) {
            i.process(new File(arg));
        }
    }


    interface IPersonalServant {
        // Process a file of commands to the servant
        void process(java.io.File f)
                throws java.io.IOException;

        boolean can(String command);
    }

}




