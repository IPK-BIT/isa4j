from isatools.model import *
from isatools import isatab
import shutil

investigation = Investigation(identifier="Investigation ID")
investigation.title = "Drought Stress Response in Arabidopsis thaliana"
#investigation.description = "An experiment about drought stress in Arabidopsis thaliana"
investigation.submission_date = "2019-01-16"

investigation.comments.extend([
    Comment(name="Owning Organisation URI", value="http://www.ipk-gatersleben.de/"),
    Comment(name="Investigation Keywords", value="plant phenotyping, image analysis, arabidopsis thaliana, lemnatec"),
    Comment(name="License", value="CC BY 4.0 (Creative Commons Attribution) - https://creativecommons.org/licenses/by/4.0/legalcode"),
    Comment(name="MIAPPE version", value="1.1"),
])


isatab.dump(investigation, ".")
shutil.copyfile("i_investigation.txt", "../../isa4J/src/test/resources/python_originals/i_investigation.txt")
