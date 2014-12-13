$(document).ready(function () {
    Morris.Bar({
        element: 'numbers',
        data: [{
            device: 'Sensor1',
            geekbench: 5
        }, {
            device: 'Sensor2',
            geekbench: 137
        }, {
            device: 'Sensor3',
            geekbench: 275
        }, {
            device: 'Sensor4',
            geekbench: 380
        }, {
            device: 'Sensor5',
            geekbench: 655
        }, {
            device: 'Sensor6',
            geekbench: 1571
        }],
        xkey: 'device',
        ykeys: ['geekbench'],
        labels: ['Geekbench'],
        barRatio: 0.4,
        xLabelAngle: 35,
        hideHover: 'auto',
        resize: true
    });

});