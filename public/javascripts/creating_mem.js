$(function() {
    var canvas_bg = $('#canvas_bg')[0];
    var context_bg = canvas_bg.getContext('2d');
    var canvas_text = $("#canvas_text")[0];
    var context_text = canvas_text.getContext('2d');
    var img = $("#source")[0];

    canvas_text.addEventListener("contextmenu", function(e) {
        save();
    });

    function save() {
        console.log("putting onto canvas_text");
        context_text.clearRect(0, 0, canvas_text.width, canvas_text.height);
        context_text.drawImage(img, 0, 0);

        var text = $("#up").val().toUpperCase();
        var textArray = splitTextIntoArray(text, context_text, canvas_text);
        drawTextOnCanvas(textArray, context_text, canvas_text, "up");

        text = $("#down").val().toUpperCase();
        textArray = splitTextIntoArray(text, context_text, canvas_text);
        drawTextOnCanvas(textArray, context_text, canvas_text, "down");

        var dataURL = canvas_text.toDataURL();
        $("#image").val(dataURL);
    }

    $("#myForm").submit(function() {
        save();
    });



    img.onload = function(){
        canvas_bg.width = canvas_text.width = img.width;
        canvas_bg.height = canvas_text.height = img.height;
        context_bg.drawImage(img,0,0);
    };

    $("#up").bind('input', function() {
        context_text.clearRect(0, 0, canvas_bg.width, canvas_bg.height/2);
        var text = $(this).val().toUpperCase();
        var textArray = splitTextIntoArray(text, context_text, canvas_bg);
        drawTextOnCanvas(textArray, context_text, canvas_bg, "up");
    });

    $("#down").bind("input", function() {
        context_text.clearRect(0, canvas_bg.height/2, canvas_bg.width, canvas_bg.height);
        var text = $(this).val().toUpperCase();
        var textArray = splitTextIntoArray(text, context_text, canvas_bg);
        drawTextOnCanvas(textArray, context_text, canvas_bg, "down");
    })
});

function splitTextIntoArray(text, context, canvas) {
    var array = text.split(" ");
    array.push(" ");
    if(array.length<1) {
        return array;
    }
    var concatenatedArray = [];
    var frontIndex = 0;
    for (var i = 1; i < array.length; i++) {
        var newText = array.slice(frontIndex, i).join(" ");
        var metrics = context.measureText(newText);
        if(metrics.width > canvas.width ) {
            concatenatedArray.push(array.slice(frontIndex, i-1).join(" "));
            frontIndex = i-1;
        }
    }
    concatenatedArray.push(array.slice(frontIndex, array.length).join(" "));
    return concatenatedArray;
}

function drawTextOnCanvas(array, context, canvas, position) {
    var step = 22;
    var x = canvas.width/2;
    var y;
    if(position=="up")
        y = 20;
    else
        y = (canvas.height-20) - step*(array.length-1);

    for(var i=0; i<array.length; i++) {
        context.font = '20px "Exo 2"';
        context.textAlign = "center";
        context.fillText(array[i], x, y+i*step);

        context.lineWidth = 1;
        context.strokeStyle = "white";
        context.strokeText(array[i], x,y+i*step);
    }
}