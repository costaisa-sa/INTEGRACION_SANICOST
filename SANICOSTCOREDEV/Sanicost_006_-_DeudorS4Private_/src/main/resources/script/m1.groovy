import com.sap.it.api.mapping.*

//Mapeo fecha - timestamp (odata)
def void dateToTimestamp(String[] arg1, Output output, MappingContext context){
    String value = arg1[0];
    String timestamp = Date.parse('yyyyMMddHHmmss', value).format("yyyy-MM-dd'T'HH:mm:ss")
    output.addValue(timestamp);
}