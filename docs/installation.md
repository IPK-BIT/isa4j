---
title: Installation
nav_order: 2
---

# Installation
The isa4j library is available from the Maven Repository:

[https://mvnrepository.com/artifact/de.ipk-gatersleben/isa4j](https://mvnrepository.com/artifact/de.ipk-gatersleben/isa4j)

You can either click on the version you want to download (we recommend using the latest version unless you have specific reasons not to) and then download the jar manually by clicking on the *jar* button under files or include one of the following snippets in your build management tool configuration file (Maven, Gradle, Ivy...).

Alternatively, you can also download the JARs from the [GitHub Repository Release page](https://github.com/IPK-BIT/isa4j/releases) or use the [Central Maven Repository search interface](https://search.maven.org/artifact/de.ipk-gatersleben/isa4j).

Please note that if you want to make use of isa4j's logging features you will have to include an [SLF4J logging implementation](http://www.slf4j.org/) in your classpath as well.

## Gradle
```
repositories {
	mavenCentral()
}    
dependencies {
	implementation group: 'de.ipk-gatersleben', name:'isa4j', version:'{{site.github.latest_release.name | split: "-" | last}}'
}
```

## Maven
```
<dependency>
  <groupId>de.ipk-gatersleben</groupId>
  <artifactId>isa4j</artifactId>
  <version>{{site.github.latest_release.name | split: "-" | last}}</version>
</dependency>
```

## Scala BST
```
libraryDependencies += "de.ipk-gatersleben" % "isa4j" % "{{site.github.latest_release.name | split: "-" | last}}"
```

## Apache Ivy
```
<dependency org="de.ipk-gatersleben" name="isa4j" rev="{{site.github.latest_release.name | split: "-" | last}}" />
```

## Groovy Grape
```
@Grapes(
  @Grab(group='de.ipk-gatersleben', module='isa4j', version='{{site.github.latest_release.name | split: "-" | last}}')
)
```

## Leiningen
```
[de.ipk-gatersleben/isa4j "{{site.github.latest_release.name | split: "-" | last}}"]
```

## Apache Buildr
```
'de.ipk-gatersleben:isa4j:jar:{{site.github.latest_release.name | split: "-" | last}}'
```

## PURL
```
pkg:maven/de.ipk-gatersleben/isa4j@{{site.github.latest_release.name | split: "-" | last}}
```
