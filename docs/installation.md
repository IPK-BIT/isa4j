---
title: Installation
nav_order: 2
---

# Installation
The isa4J library is available from the Central Maven Repository:

[https://search.maven.org/artifact/de.ipk-gatersleben/isa4J](https://search.maven.org/artifact/de.ipk-gatersleben/isa4J)


You can either click on the version you want to download (we recommend using the latest version unless you have specific reasons not to) and then download the jar manually by clicking on the *Downloads* button or include the right one of the following snippets in your build management tool configuration file (Maven, Gradle, Ivy...).

Alternatively, you can download the JARs from the [GitHub Repository Release page](https://github.com/IPK-BIT/isa4J/releases).

## Gradle
```
repositories {
	mavenCentral()
}    
dependencies {
	compile group: 'de.ipk-gatersleben', name:'isa4j', version:'{{site.github.latest_release.name | split: "-" | last}}'
}
```

## Maven
```
<dependency>
  <groupId>de.ipk-gatersleben</groupId>
  <artifactId>isa4J</artifactId>
  <version>{{site.github.latest_release.name | split: "-" | last}}</version>
</dependency>
```
