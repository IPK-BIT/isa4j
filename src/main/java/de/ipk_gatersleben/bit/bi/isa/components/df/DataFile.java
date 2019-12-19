package de.ipk_gatersleben.bit.bi.isa.components.df;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import de.ipk_gatersleben.bit.bi.isa.components.tdf.Trait;
import de.ipk_gatersleben.bit.bi.isa.constants.Symbol;

public class DataFile {

	private List<DataEntry> dataEntries = new ArrayList<DataEntry>();

	public DataFile() {

	}

	public DataFile(List<DataEntry> dataEntries) {
		this.dataEntries = dataEntries;
	}

	public List<DataEntry> getDataEntries() {
		return dataEntries;
	}

	public void setDataEntries(List<DataEntry> dataEntries) {
		this.dataEntries = dataEntries;
	}

	public void addDataEntry(DataEntry dataEntry) {
		this.dataEntries.add(dataEntry);
	}

	public void writeToFile(Path filePath) {

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(filePath.toFile()));

			/** write header **/

			writer.write("ObservationUnit" + Symbol.TAB.toString());
			writer.write("AssayName" + Symbol.TAB.toString());
			writer.write("Date" + Symbol.TAB.toString());

			for (DataEntry entry : dataEntries) {
				for (Entry<Trait, String> value : entry.getValues().entrySet()) {
					writer.write(value.getKey().getVariable().getId() + Symbol.TAB.toString());
				}
				writer.newLine();
				break;
			}

			/** write data **/

			for (DataEntry entry : dataEntries) {
				writer.write(entry.getObservationUnit() + Symbol.TAB.toString());
				writer.write(entry.getAssayName() + Symbol.TAB.toString());
				writer.write(entry.getDate().getTime() + Symbol.TAB.toString());

				for (Entry<Trait, String> value : entry.getValues().entrySet()) {
					writer.write(value.getValue() + Symbol.TAB.toString());
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
			os.write(("ObservationUnit" + Symbol.TAB.toString()).getBytes());
			os.write(("AssayName" + Symbol.TAB.toString()).getBytes());
			os.write(("Date" + Symbol.TAB.toString()).getBytes());

			for (DataEntry entry : dataEntries) {
				for (Entry<Trait, String> value : entry.getValues().entrySet()) {
					os.write((value.getKey().getVariable().getId() + Symbol.TAB.toString()).getBytes());
				}
				os.write("\n".getBytes());
				break;
			}

			for (DataEntry entry : dataEntries) {
				os.write((entry.getObservationUnit() + Symbol.TAB.toString()).getBytes());
				os.write((entry.getAssayName() + Symbol.TAB.toString()).getBytes());
				os.write((entry.getDate().getTime() + Symbol.TAB.toString()).getBytes());

				for (Entry<Trait, String> value : entry.getValues().entrySet()) {
					os.write((value.getValue() + Symbol.TAB.toString()).getBytes());
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
