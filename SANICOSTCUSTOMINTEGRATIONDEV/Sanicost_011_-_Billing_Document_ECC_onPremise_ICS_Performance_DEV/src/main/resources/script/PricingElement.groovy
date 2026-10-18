import com.sap.it.api.mapping.*

@FunctionParam(queueName="conditionType")
@FunctionParam(queueName="conditionRateValue")
public void getPMP0Price(String[] conditionType, String[] conditionRateValue, 
                          ResultList result, MappingContext context) {
    String precio = ""
    for (int i = 0; i < conditionType.length; i++) {
        if ("PMP0".equals(conditionType[i])) {
            precio = conditionRateValue[i]
            break
        }
    }
    result.addValue(precio)
}
