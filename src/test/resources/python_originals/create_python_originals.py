from isatools.model import *
from isatools import isatab
import shutil

investigation = Investigation(identifier="Investigation ID")
investigation.title = "Drought Stress Response in Arabidopsis thaliana"
investigation.description = "An experiment about drought stress in Arabidopsis thaliana"
investigation.submission_date = "2019-01-16"


isatab.dump(investigation, ".")
shutil.copyfile("i_investigation.txt", "../../isa4J/src/test/resources/python_originals/i_investigation.txt")
