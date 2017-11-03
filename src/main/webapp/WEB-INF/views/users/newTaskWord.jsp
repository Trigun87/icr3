<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="it.uniroma3.icr.model.Job"%>
<%@ page import="it.uniroma3.icr.model.Task"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/newTaskWord.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->

<!-- Favicon -->
<link rel="shortcut icon"
	href="<c:url value="${pageContext.request.contextPath}/img/siteImages/favicon.ico"/>">
</head>

<body class="landing">
	<ul>
		<li>
			<h2>${student.name}${student.surname}</h2>

			<h2>Stai svolgendo il task relativo al ${task.job.title}
				${task.job.symbol.transcription}</h2>
		</li>

	</ul>

	<div align="center">
		<h3>Seleziona le parti della lettera che ti sembrano simili agli esempi
			sottostanti</h3>

		<table class="pos">
			<tr>
				<c:forEach varStatus="vs" var="sample" items="${positiveSamples}">

					<td><img
						src="${pageContext.request.contextPath}/${sample.path}"
						alt="${sample.id}" /></td>
				</c:forEach>
			</tr>
		</table>

	</div>

	<div align="center">
		<h3>Attenzione: non vanno bene immagini come queste</h3>
		<table class="neg">
			<tr>
				<c:forEach varStatus="vs" var="sample" items="${negativeSamples}">
					<td><img
						src="${pageContext.request.contextPath}/${sample.path}"
						alt="${sample.id}" /></td>

				</c:forEach>
			</tr>
		</table>
	</div>


	<form:form method="post" action="secondConsoleWord"
		modelAttribute="taskResults" name="form">


		<div align="center">
			<c:forEach varStatus="vs" var="result"
				items="${taskResults.resultList}">
				<!--
					<c:choose>
						<c:when test="${vs.index == 0}">
							<c:set var="previousWord" value="${result.image.word.name}"></c:set>
						</c:when>
						<c:when test="${vs.index > 0}">
							<c:if test="${previousWord ne result.image.word.name}">
								-->
				<br />
				<!--
							</c:if>
							<c:set var="previousWord" value="${result.image.word.name}"></c:set>
						</c:when>
					</c:choose>-->
				<div id="canvasWrapper"></div>
				<button type=button id="undotoStart">RESTART</button>
				<button type=button id="undo">UNDO</button>
				<form:input type="hidden" id="output" path="resultList[${vs.index}].answer" /></div>
				<br>
				<label for="resultList[${vs.index}].id"></label>
				<form:hidden path="resultList[${vs.index}].id" />
				<form:hidden path="resultList[${vs.index}].image.id" />
				<form:hidden path="resultList[${vs.index}].task.id" />
				<form:hidden path="resultList[${vs.index}].task.student.id" />
				<form:hidden path="resultList[${vs.index}].task.batch" />
				<form:hidden path="resultList[${vs.index}].task.job.id" />
				<form:hidden path="resultList[${vs.index}].task.startDate" />
			</c:forEach>
		</div>

		<div align="center">
			<input type="submit" name="action"
				value="Conferma e vai al prossimo task">
		</div>

		<br />

		<div align="center">
			<input type="submit" name="action"
				value="Conferma e torna alla pagina dello studente">
		</div>

	</form:form>

	<div align="center">
		<form:form method="post" action="homeStudent">
			<input type="submit" value="Torna alla pagina dello studente">
		</form:form>

	</div>


	<!-- Scripts -->
	<script>
var ExtendedCanvas = (function() {
    var context, data, dataOrigArr, dataOrig, canvas, output;
    function ExtendedCanvas(selector, imageSrc) {
        var wrapper = document.querySelector(selector);
        this.element = canvas = document.createElement('canvas');
        context = this.element.getContext('2d');
        loadImage.call(this, imageSrc, function(image) {
            canvas.setAttribute('width', image.width*3);
            canvas.setAttribute('height', image.height*3);
            context.webkitImageSmoothingEnabled = false;
            context.mozImageSmoothingEnabled = false;
            context.imageSmoothingEnabled = false; /// future
            context.drawImage(image,0,0,image.width*3, image.height*3);
            data = context.getImageData(0,0,canvas.width, canvas.height);
            dataOrigArr = context.getImageData(0,0,canvas.width, canvas.height).data;
            dataOrig = canvas.toDataURL();
        });
        wrapper.appendChild(this.element);
        output = [];
    }

    function loadImage(src, cb) {
        var image = new Image();
        var canvas = this.element;
        image.onload = function() {
            cb(this);
        }
        image.crossOrigin = 'Anonymous';
        image.src = src;
    }

    ExtendedCanvas.prototype.getPixelIndex = function(x, y) {
        return (Math.floor(y) * canvas.width + Math.floor(x)) * 4;
    }

    ExtendedCanvas.prototype.getPixelColor = function(x, y) {
        var index = this.getPixelIndex(x, y);
        var d =  data.data;
        var r = d[index];
        var g = d[index + 1];
        var b = d[index + 2];
        var a = d[index + 3];
        return [r, g, b, a];
    }
    ExtendedCanvas.prototype.getOriginalPixelColor = function(x, y) {
        var index = this.getPixelIndex(x, y);
        var d =  dataOrigArr;
        var r = d[index];
        var g = d[index + 1];
        var b = d[index + 2];
        var a = d[index + 3];
        return [r, g, b, a];
    }

    ExtendedCanvas.prototype.setPixelColor = function(x, y, color) {
        var index = this.getPixelIndex(x, y);
        var d = data.data;
        d[index] = color[0];
        d[index + 1] = color[1];
        d[index + 2] = color[2];
        d[index + 3] = color[3];
    }


    ExtendedCanvas.prototype.fill = function(x, y, fillColor) {
        if(x < 0 || y < 0 || x > canvas.width || y > canvas.height) {
            return;
        }

        fillColor = fillColor || [0,0,0,255];
        var color = this.getPixelColor(x, y).join();

        if(color === fillColor || color === [255,255,255,255].join()) {
            return;
        }

        if(color === fillColor.join()) {
            return;
        }
        output.push(color);
        temp = [];
        this.fillImg(fillColor);


    }

    ExtendedCanvas.prototype.fillImg = function(fillColor) {
        var stack = [];
        context.fillStyle = fillColor;
        for (var i = 0; i<canvas.width;i++)
            for (var j = 0; j<canvas.height;j++)
                stack.push([i, j]);

        while(stack.length > 0) {
            var position = stack.pop();
            var posX = position[0];
            var posY = position[1];
            var posColor = this.getPixelColor(posX, posY).join();
            for (k in output)
                if(posColor === output[k]) {
                    this.setPixelColor(posX, posY, fillColor);
                }
        }
        context.putImageData(data, 0, 0);
    }


    ExtendedCanvas.prototype.getOutput = function() {
        return output;
    }

    ExtendedCanvas.prototype.undo = function() {
        if (output.length>0){
            fillColor = output.pop();
            var stack = [];
            context.fillStyle = fillColor.split(",");

            for (var i = 0; i<canvas.width;i++)
                for (var j = 0; j<canvas.height;j++)
                    stack.push([i, j]);

            while(stack.length > 0) {
                var position = stack.pop();
                var posX = position[0];
                var posY = position[1];
                var posColor = this.getOriginalPixelColor(posX, posY).join();
                if(posColor === fillColor) {
                    this.setPixelColor(posX, posY, fillColor.split(","));
                }
            }
            context.putImageData(data, 0, 0);
        }
    }

    ExtendedCanvas.prototype.undoToStart = function() {

        var canvasPic = new Image();
        output = [];
        canvasPic.src = dataOrig;
        canvasPic.onload = function() {
            context.drawImage(canvasPic, 0, 0);
            data = context.getImageData(0,0,canvas.width, canvas.height);
        };
    }
    return ExtendedCanvas;
})();

document.addEventListener('DOMContentLoaded', function() {
    var c = new ExtendedCanvas('#canvasWrapper', '${pageContext.request.contextPath}/${taskResults.resultList[0].image.path}');

    c.element.addEventListener('click', function(e) {
        var x = e.pageX - this.offsetLeft;
        var y = e.pageY - this.offsetTop;
        c.fill(x, y);
        $("#output")[0].value = JSON.stringify(c.getOutput());
    });

    $("#undo").click(function() {
        c.undo();
        $("#output")[0].value = JSON.stringify(c.getOutput());
    });
    $("#undotoStart").click(function() {
        c.undoToStart();
        $("#output")[0].value = JSON.stringify(c.getOutput());
    });
});


  </script>
	<script src="${pageContext.request.contextPath}/js/selectAll.js"></script>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/jquery.scrollex.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/jquery.scrolly.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/util.js"></script>
	<!--[if lte IE 8]><script src="/js/ie/respond.min.js"></script><![endif]-->
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
	<script src="${pageContext.request.contextPath}/js/backButton.js"></script>


</body>
</html>