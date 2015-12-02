# A simple example of a Java Service Provider (SPI) mechanism

## Introduction
This is a simple implementation of the [SPI](https://docs.oracle.com/javase/tutorial/ext/basics/spi.html) extension mechanism from Java.

  * The whole extension SPI mechanism is in the package work.
  * An example of a service (ie extension) is in the package workExtension. The registration occurs with the file under the resources/META-INF/services
that points to the work provider. In this example, to HelloWorkProvider


The whole mechanism is really well explain in the java doc of the [java.util.ServiceLoader(http://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html).

This code was made by taking as example the nio FileSystemProvider implementation. [java.nio.file.spi.FileSystemProvider](https://docs.oracle.com/javase/8/docs/api/java/nio/file/spi/FileSystemProvider.html)

## Documentation / Reference

  * [Java - Service Provider Interface (SPI)](http://gerardnico.com/wiki/language/java/spi)