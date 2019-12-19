package de.ipk_gatersleben.bit.bi.isa.components.tdf;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import de.ipk_gatersleben.bit.bi.isa.constants.Symbol;

public class TraitDefinition {

	private List<Trait> traits = new ArrayList<Trait>();

	public TraitDefinition() {

	}

	public TraitDefinition(List<Trait> traits) {
		this.setTraits(traits);
	}

	public List<Trait> getTraits() {
		return traits;
	}

	public void setTraits(List<Trait> traits) {
		this.traits = traits;
	}

	public void addTrait(Trait trait) {
		this.traits.add(trait);
	}

	public void addAllTraits(List<Trait> traits) {
		this.traits.addAll(traits);
	}

	public void writeToFile(Path file) {

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file.toFile()));
			writer.write("Variable ID" + Symbol.TAB.toString());
			writer.write("Variable Name" + Symbol.TAB.toString());
			writer.write("Variable Acession Number" + Symbol.TAB.toString());
			writer.write("Trait" + Symbol.TAB.toString());
			writer.write("Trait Accession Number" + Symbol.TAB.toString());
			writer.write("Method" + Symbol.TAB.toString());
			writer.write("Method Accession Number" + Symbol.TAB.toString());
			writer.write("Method Description" + Symbol.TAB.toString());
			writer.write("Method Reference" + Symbol.TAB.toString());
			writer.write("Scale" + Symbol.TAB.toString());
			writer.write("Scale Accession Number" + Symbol.TAB.toString());
			writer.write("Time Scale" + Symbol.TAB.toString());
			writer.newLine();

			for (Trait trait : this.traits) {
				writer.write(trait.getVariable().getId() + Symbol.TAB);
				writer.write(trait.getVariable().getName() + Symbol.TAB);
				writer.write(trait.getVariable().getAccessionNumber() + Symbol.TAB);
				writer.write(trait.getName() + Symbol.TAB);
				writer.write(trait.getAccessionNumber() + Symbol.TAB);
				writer.write(trait.getMethod().getName() + Symbol.TAB);
				writer.write(trait.getMethod().getAccessionNumber() + Symbol.TAB);
				writer.write(trait.getMethod().getDescription() + Symbol.TAB);
				if (trait.getMethod().getAssociatedReference() != null) {
					writer.write(trait.getMethod().getAssociatedReference().getDOI() + Symbol.TAB);
				}
				if (trait.getScale() != null) {
					writer.write(trait.getScale().getName() + Symbol.TAB);
					writer.write(trait.getScale().getAccesssionNumber() + Symbol.TAB);
					writer.write(trait.getScale().getTimeScale() + Symbol.TAB);
				}
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToStream(OutputStream os, boolean closeStreamAfterWriting) {

		try {
			os.write(("Variable ID" + Symbol.TAB.toString()).getBytes());
			os.write(("Variable Name" + Symbol.TAB.toString()).getBytes());
			os.write(("Variable Acession Number" + Symbol.TAB.toString()).getBytes());
			os.write(("Trait" + Symbol.TAB.toString()).getBytes());
			os.write(("Trait Accession Number" + Symbol.TAB.toString()).getBytes());
			os.write(("Method" + Symbol.TAB.toString()).getBytes());
			os.write(("Method Accession Number" + Symbol.TAB.toString()).getBytes());
			os.write(("Method Description" + Symbol.TAB.toString()).getBytes());
			os.write(("Method Reference" + Symbol.TAB.toString()).getBytes());
			os.write(("Scale" + Symbol.TAB.toString()).getBytes());
			os.write(("Scale Accession Number" + Symbol.TAB.toString()).getBytes());
			os.write(("Time Scale" + Symbol.TAB.toString()).getBytes());
			os.write("\n".getBytes());

			for (Trait trait : this.traits) {
				os.write((trait.getVariable().getId() + Symbol.TAB).getBytes());
				os.write((trait.getVariable().getName() + Symbol.TAB).getBytes());
				os.write((trait.getVariable().getAccessionNumber() + Symbol.TAB).getBytes());
				os.write((trait.getName() + Symbol.TAB).getBytes());
				os.write((trait.getAccessionNumber() + Symbol.TAB).getBytes());
				os.write((trait.getMethod().getName() + Symbol.TAB).getBytes());
				os.write((trait.getMethod().getAccessionNumber() + Symbol.TAB).getBytes());
				os.write((trait.getMethod().getDescription() + Symbol.TAB).getBytes());
				if (trait.getMethod().getAssociatedReference() != null) {
					os.write((trait.getMethod().getAssociatedReference().getDOI() + Symbol.TAB).getBytes());
				}
				if (trait.getScale() != null) {
					os.write((trait.getScale().getName() + Symbol.TAB).getBytes());
					os.write((trait.getScale().getAccesssionNumber() + Symbol.TAB).getBytes());
					os.write((trait.getScale().getTimeScale() + Symbol.TAB).getBytes());
				}
				os.write("\n".getBytes());
            }
            
            if (closeStreamAfterWriting) {
                os.close();
            }
            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
