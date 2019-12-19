package de.ipk_gatersleben.bit.bi.isa.util;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public aspect TimeAspect {

    long start = 0;
    long end = 0;
    private final String ENTER = System.getProperty("line.separator");

    pointcut timeMeasuring():
            execution(boolean de.ipk_gatersleben.bit.bi.isa.Investigation.writeToFile(*,*,*));

    before(): timeMeasuring() {
        start = System.currentTimeMillis();
//        System.out.println("before !" + thisJoinPointStaticPart.getSignature().toString());
    }

    after() returning: timeMeasuring() {
        end = System.currentTimeMillis();
        System.out.println("runtime: "+(float) (end - start) / 1000 + "s");
    }

}