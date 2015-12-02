/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package work;

import java.net.URI;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

/**
 * Service-provider class
 * <p/>
 * A service is normally just the definition of a method/interface.
 * <p/>
 * The methods defined in a static class will typically delegate to an instance of this
 * class.
 * <p/>
 * A service provider is a concrete implementation of this class that
 * implements the abstract methods defined by this class.
 * <p/>
 * A provider is identified by a {@code URI} {@link #getScheme() scheme}.
 * <p/>
 * A factory method class defines how providers are located
 * and loaded.
 * The first invocation of a method (such as installedProviders)
 * locates and loads all installed file system providers.
 * Installed providers are loaded using the service-provider loading facility defined by the ServiceLoader class.
 * Installed providers are loaded using the system class loader.
 * If the system class loader cannot be found then the extension class loader is used; if there is no extension class loader then the bootstrap class loader is used.
 * <p/>
 * Default parameters may be overridden by setting a system property.
 * <p/>
 * All providers have generally zero argument constructor that initializes the provider.
 * <p/>
 * A provider is a factory for one or more (service) object instances.
 * Each service is identified by a {@code URI} where the URI's scheme matches
 * the provider's {@link #getScheme scheme}.
 * <p/>
 * Inspired by {@link java.nio.file.spi.FileSystemProvider}
 */

public abstract class WorkProvider {

    // lock using when loading providers
    private static final Object lock = new Object();

    // installed providers
    private static volatile List<WorkProvider> installedProviders;

    // used to avoid recursive loading of installed providers
    private static boolean loadingProviders = false;

    private static Void checkPermission() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
            sm.checkPermission(new RuntimePermission("fileSystemProvider"));
        return null;
    }

    private WorkProvider(Void ignore) {
    }

    /**
     * Initializes a new instance of this class.
     * <p/>
     * <p> During construction a provider may safely access files associated
     * with the default provider but care needs to be taken to avoid circular
     * loading of other installed providers. If circular loading of installed
     * providers is detected then an unspecified error is thrown.
     *
     * @throws SecurityException If a security manager has been installed and it denies
     *                           {@link RuntimePermission}<tt>("fileSystemProvider")</tt>
     */
    protected WorkProvider() {
        this(checkPermission());
    }

    // loads all installed providers
    private static List<WorkProvider> loadInstalledProviders() {
        List<WorkProvider> list = new ArrayList<WorkProvider>();

        ServiceLoader<WorkProvider> sl = ServiceLoader
                .load(WorkProvider.class, ClassLoader.getSystemClassLoader());

        // ServiceConfigurationError may be throw here
        for (WorkProvider provider : sl) {
            String scheme = provider.getScheme();

            boolean found = false;
            for (WorkProvider p : list) {
                if (p.getScheme().equalsIgnoreCase(scheme)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                list.add(provider);
            }

        }
        return list;
    }

    /**
     * Returns a list of the installed work providers.
     * <p/>
     * <p> The first invocation of this method loads any installed
     * providers that will be used by the provider Factory class.
     *
     * @return An unmodifiable list of the installed service providers.
     * @throws ServiceConfigurationError When an error occurs while loading a service provider
     */
    public static List<WorkProvider> installedProviders() {
        if (installedProviders == null) {

            synchronized (lock) {
                if (installedProviders == null) {
                    if (loadingProviders) {
                        throw new Error("Circular loading of installed providers detected");
                    }
                    loadingProviders = true;

                    List<WorkProvider> list = AccessController
                            .doPrivileged(new PrivilegedAction<List<WorkProvider>>() {
                                @Override
                                public List<WorkProvider> run() {
                                    return loadInstalledProviders();
                                }
                            });

                    installedProviders = Collections.unmodifiableList(list);
                }
            }
        }
        return installedProviders;
    }

    /**
     * Returns the URI scheme that identifies this provider.
     *
     * @return The URI scheme
     */
    public abstract String getScheme();

    /**
     * Constructs a new {@code Work} object identified by a URI. This
     * method is invoked by the {@link WorkProvider#newWork(URI, Map)}
     * method to open a new work identified by a URI.
     * <p/>
     * <p> The {@code uri} parameter is an absolute, hierarchical URI, with a
     * scheme equal (without regard to case) to the scheme supported by this
     * provider. The exact form of the URI is highly provider dependent. The
     * {@code env} parameter is a map of provider specific properties to configure
     * the work.
     * <p/>
     * <p> This method may throws an exception if the
     * work already exists because it was previously created by an
     * invocation of this method.
     *
     * @param uri URI reference
     * @param env A map of provider specific properties to configure the file system;
     *            may be empty
     * @return A new work
     */
    public abstract Work newWork(URI uri, Map<String, ?> env);

    /**
     * Returns an existing {@code work} created by this provider.
     * <p/>
     * <p> This method returns a reference to a {@code work} that was
     * created by invoking the {@link #newWork(URI, Map)}
     * method.
     * The work is identified by its {@code URI}. Its exact form
     * is highly provider dependent.
     * <p/>
     * <p> If a security manager is installed then a provider implementation
     * may require to check a permission before returning a reference to an
     * existing work.
     *
     * @param uri URI reference
     * @return The work
     * @throws SecurityException           If a security manager is installed and it denies an unspecified
     *                                     permission.
     */
    public abstract Work getWork(URI uri);


    /**
     * Others providers methods if needed
     */
}
