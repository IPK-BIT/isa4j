require "nokogiri"
require "open-uri"
require "pry"

doc = Nokogiri::XML(URI.open("https://raw.githubusercontent.com/MIAPPE/ISA-Tab-for-plant-phenotyping/v1.1/isaconfig-phenotyping/isaconfig-phenotyping-basic/i_investigation.xml"))

doc.css("field").each do |field|
  if field["header"].start_with?("Comment[")
    header = field["header"][8..-2]
    name = header.upcase.gsub(" ", "_")
    section = field["section"].gsub(" ", "_")
    description = field.content.gsub("\n", " ").lstrip
    required = field["is-required"]
    description = "[required]" + description if(required == "true")

    puts "
    /**
     * #{description}
     */
    #{name}(\"#{header}\", InvestigationAttribute.#{section}, #{required}),"
  end
end

#binding.pry
