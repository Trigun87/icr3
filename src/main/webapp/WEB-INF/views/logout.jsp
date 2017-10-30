<form name="submitForm1" method="POST"
	action="${pageContext.request.contextPath}/logout">
	<input type="hidden" name="_method" value="delete" />

</form>
<script type="text/javascript">
	$(document).ready(function() {
		document.submitForm1.submit();
	});
</script>