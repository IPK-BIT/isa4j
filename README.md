[![Maven Central](https://img.shields.io/maven-central/v/de.ipk-gatersleben/isa4J.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22de.ipk-gatersleben%22%20AND%20a:%22isa4J%22)

## Welcome
isa4J is a comprehensive and scalable Java Library for the programmatic generation of experimental metadata descriptions using the ISATab container format.
We're assuming you're familiar with the ISA-Tab framework in the remainder of the manual; if you're not, please [read up about it first](https://isa-specs.readthedocs.io/en/latest/).

## License and Citation
The software provided as-is and made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html), granting you the freedom to run, use, study, share, and modify the software in any way you want as long as any derivative work is distributed under the same or equivalent terms ([details](https://en.wikipedia.org/wiki/GNU_General_Public_License#Terms_and_conditions)).
If you're referring to isa4J in a scientific publication, we'd be grateful if you could cite our paper:

> Citation forthcoming

## Usage via common build management tools

### Gradle

```groovy
repositories {
	mavenCentral()
}    
dependencies {
	compile group: 'de.ipk-gatersleben', name:'isa4j', version:'0.0.2'
}

```

### Maven
```
<dependency>
  <groupId>de.ipk-gatersleben</groupId>
  <artifactId>isa4J</artifactId>
  <version>0.0.2</version>
</dependency>
```

## Documentation
Please see https://ipk-bit.github.io/isa4J/ for installation and usage documentation.
