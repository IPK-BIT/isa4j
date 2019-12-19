/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa.util;

import de.ipk_gatersleben.bit.bi.isa.Investigation;
import de.ipk_gatersleben.bit.bi.isa.components.Unit;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;

/**
 * This class is the tool class for Unit. It used to save the run memory.
 * The component has only the name of Unit. And the total Unit is saved a list of investigation.
 */
public class UnitMappingUtil {
    /**
     * Add a unit to the collection. The collection is saved in investigation.
     * Every time a component get a unit, it must be checked, if it already in the collection.
     *
     * @param unit     unit of parameter
     * @param unitName name of unit
     */
    public static void addUnitToCollection(Unit unit, String unitName) {

        if (Investigation.unitMap.get(unitName) == null) {
            if (unit.getOntology() == null) {
                Investigation.unitMap.put(unit.getName(), OntologyAnnotation.blankOntology());
            } else {
                Investigation.unitMap.put(unit.getName(), unit.getOntology());
            }
        } else {
            OntologyAnnotation unitOntology = Investigation.unitMap.get(unitName);
            if (unit.getOntology() != null) {
                if (!unitOntology.getSourceREF().equals(unit.getOntology().getSourceREF()) ||
                        !unitOntology.getTermAccession().equals(unit.getOntology().getTermAccession())) {
                    LoggerUtil.logger.error("The Unit '" + unitName + "' has 2 different ontologyTerms. Please check it.");
                }
            }
        }
    }
}
