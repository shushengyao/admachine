var http = require('http')

var options = {
    host:'localhost', //check this
    port: 8081,  //check this
    method: 'POST',
    path:'/command/y10-518-00147', //check this
    //path:'/command/m90-917-08888',
    headers:{'Content-Type':"application/json; charset=UTF-8"}
}

var req = http.request(options, function(res) {
    // console.log('STATUS: ' + res.statusCode);
    //console.log('HEADERS: ' + JSON.stringify(res.headers));
    res.setEncoding('utf8');
    var arr = []
    res.on('data', function (chunk) {
        arr.push(chunk)
        // console.log('BODY: ' + chunk);
    });
    res.on('end', function(){
        //console.log('BODY: ' + chunk);
        var data = arr.join()
        data = data.replace(/\n/g,'')
        data = JSON.parse(data)
        console.log(data)
        var fs = require('fs')
        fs.writeFileSync('test.png',data.result,'base64')
    })
});

req.on('error', function(e) {
    console.log('problem with request: ' + e.message);
});

var request = {
    "type":'loadUrl',
    url:'http://192.168.6.104:8081/demo.html',//url:'file:///mnt/sdcard/test.html, //也      可以是本地路径
    persistent:false //持久化，重启会自动加载url
}

// write data to request body
req.write(JSON.stringify(request));
req.end();