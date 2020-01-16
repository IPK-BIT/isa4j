from isatools.model import *
from isatools import isatab
import shutil

investigation = Investigation(identifier="Investigation ID")


isatab.dump(investigation, ".")
shutil.copyfile("i_investigation.txt", "../../isa4J/src/test/resources/python_originals/i_investigation.txt")
