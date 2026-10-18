importClass(com.sap.gateway.ip.core.customdev.util.Message);
importClass(java.util.HashMap);
function processData(message) {
	var body = message.getBody(new java.lang.String().getClass());

	message.setProperty("Billing_Document", ""+body["n0:BillingDocumentConfirmation"].BillingDocument);
	return message;
}