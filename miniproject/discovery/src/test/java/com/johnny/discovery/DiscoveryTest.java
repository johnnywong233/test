package com.johnny.discovery;

import com.johnny.discovery.impl.CreateAction;
import com.johnny.discovery.impl.DeleteAction;
import com.johnny.discovery.impl2.AddAction;
import com.johnny.discovery.impl2.RemoveAction;
import junit.framework.TestCase;
import org.apache.commons.discovery.Resource;
import org.apache.commons.discovery.ResourceIterator;
import org.apache.commons.discovery.jdk.JDKHooks;
import org.apache.commons.discovery.resource.ClassLoaders;
import org.apache.commons.discovery.resource.DiscoverResources;
import org.apache.commons.discovery.tools.*;

import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * All providers which implement the Action Interface can be configured
 * in the service default configuration file
 * /META-INF/services/com.johnny.discovery.Action
 * <p>
 * Use reflect to find the implementations of super interface.
 */
public class DiscoveryTest extends TestCase {

    /**
     * CreateAction/DeleteAction/AddAction have been defined in /META-INF/services/com.johnny.discovery.Action
     * <p>
     * And the order is CreateAction > DeleteAction > AddAction
     */
    @SuppressWarnings("unchecked")
    public void testGetAllProviders() {
        String[] expectedResults = new String[]{"Create Action", "Delete Action", "Add Action"};

        Enumeration<Action> enu = Service.providers(Action.class);
        int count = 0;
        while (enu.hasMoreElements()) {
            Action action = enu.nextElement();
            assertTrue("The action name should be \"" + expectedResults[count] + "\", but actually is \"" + action.getName() + "\"",
                    action.getName().equals(expectedResults[count]));
            count++;
        }
        assertEquals(count, expectedResults.length);
    }

    /**
     * Create Action has been defined in /META-INF/services/com.johnny.discovery.Action
     * <p>
     * And the order is CreateAction > DeleteAction > AddAction
     */
    public void testFindCreateAction() {
        try {
            // Load provider com.johnny.discovery.impl.CreateAction
            Action createAction = DiscoverSingleton.find(Action.class, CreateAction.class.getName());
            assertEquals("Create Action", createAction.getName());
        } finally {
            DiscoverSingleton.release();
        }
    }

    /**
     * Delete Action has been defined in /META-INF/services/com.johnny.discovery.Action
     * <p>
     * And the order is CreateAction > DeleteAction > AddAction
     */
    public void testFindDeleteActionInConfig() {
        try {
            // Load provider com.johnny.discovery.impl.CreateAction
            Action deleteAction = DiscoverSingleton.find(Action.class, DeleteAction.class.getName());

            // As the default configuration file defines the CreateAction as the first element, so you will always get the CreateAction as singleton. 
            assertEquals("Create Action", deleteAction.getName());
        } finally {
            DiscoverSingleton.release();
        }
    }

    /**
     * Use individual properties to load DeleteAction.
     */
    public void testFindDeleteActionWithProperty() {
        try {
            Properties props = new Properties();
            props.setProperty(Action.class.getName(), DeleteAction.class.getName());

            // Load provider com.johnny.discovery.impl.CreateAction
            Action deleteAction = DiscoverSingleton.find(Action.class, props);
            assertEquals("Delete Action", deleteAction.getName());
        } finally {
            DiscoverSingleton.release();
        }
    }

    /**
     * RemoveAction hasn't been defined in /META-INF/services/com.johnny.discovery.Action
     * But you will still get CreateAction
     */
    public void testFindRemoveAction() {
        try {
            // Load provider com.johnny.discovery.impl.RemoveAction
            Action removeAction = DiscoverSingleton.find(Action.class, RemoveAction.class.getName());
            assertEquals("Create Action", removeAction.getName());
        } finally {
            DiscoverSingleton.release();
        }
    }

    //单独跑的话，是 OK 的，跑这个 test class 则会 assert 失败
    //变成 maven 工程之后，没有这个问题。
    public void testCache() {
        try {
            // Load provider com.johnny.discovery.impl.CreateAction
            Action action = DiscoverSingleton.find(Action.class, CreateAction.class.getName());
            assertEquals("Create Action", action.getName());

            // Want to load provider com.johnny.discovery.impl.DeleteAction
            Properties props = new Properties();
            props.setProperty(Action.class.getName(), DeleteAction.class.getName());
            action = DiscoverSingleton.find(Action.class, props);

            // But actually, the DiscoverSingleton hasn't been released, so should get cached value...
            assertEquals("Create Action", action.getName());
        } finally {
            DiscoverSingleton.release();
        }
    }

    public void testRelease() {
        Action action;
        try {
            // Load provider com.johnny.discovery.impl.CreateAction
            action = DiscoverSingleton.find(Action.class, CreateAction.class.getName());
            assertEquals("Create Action", action.getName());
            DiscoverSingleton.release();

            // Want to load provider com.johnny.discovery.impl.DeleteAction
            Properties props = new Properties();
            props.setProperty(Action.class.getName(), DeleteAction.class.getName());
            action = DiscoverSingleton.find(Action.class, props);

            // The DiscoverSingleton has been released, so get the DeleteAction
            assertEquals("Delete Action", action.getName());
        } finally {
            DiscoverSingleton.release();
        }
    }

    public void testManageProperty() {
        try {
            ManagedProperties.setProperty(Action.class.getName(), AddAction.class.getName());
            Action addAction = DiscoverSingleton.find(Action.class);

            assertEquals("Add Action", addAction.getName());
        } finally {
            DiscoverSingleton.release();

            // Cleanup, don't want to affect next test..
            ManagedProperties.setProperty(Action.class.getName(), null);
        }
    }

    @SuppressWarnings("unchecked")
    public void testFindPropFileDefault() {
        Action action = (Action) DiscoverSingleton.find(null,
                new SPInterface(Action.class),
                new PropertiesHolder("Action.properties"),
                new DefaultClassHolder(DeleteAction.class));

        //TODO
        assertEquals("Remove Action", action.getName());
    }

    public void testFindResources() {
        ClassLoaders loaders = new ClassLoaders();
        ClassLoader cl = getClass().getClassLoader();
        if (cl != null) {
            loaders.put(getClass().getClassLoader(), true);
        } else {
            loaders.put(JDKHooks.getJDKHooks().getSystemClassLoader(), true);
        }

        String name = "conf/testResource";
        DiscoverResources discovery = new DiscoverResources(loaders);
        ResourceIterator iter = discovery.findResources(name);

        while (iter.hasNext()) {
            Resource resource = iter.nextResource();
            URL url = resource.getResource();
            System.out.println(url);
        }
    }

}
