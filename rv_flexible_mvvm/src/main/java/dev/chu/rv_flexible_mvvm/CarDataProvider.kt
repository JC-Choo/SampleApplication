package dev.chu.rv_flexible_mvvm

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class CarData(
    val id: Long,
    val make: String,
    val model: String,
    val price: String,
    val isAd: Boolean
)

class CarDataProvider {

    private val gson = Gson()

    fun getCarListData(): List<CarData> {
        val typeToken = object : TypeToken<List<CarData>>(){}.type
        return gson.fromJson(getCarDataString(), typeToken)
    }

    private fun getCarDataString(): String {
        return """
            [{"id":1,"make":"Suzuki","model":"Swift","price":"${'$'}14328.73"},
            {"id":2,"make":"Subaru","model":"Legacy","price":"${'$'}40656.87","isAd":true},
            {"id":3,"make":"BMW","model":"3 Series","price":"${'$'}5408.29"},
            {"id":4,"make":"Jeep","model":"Wrangler","price":"${'$'}67195.92"},
            {"id":5,"make":"Chevrolet","model":"Aveo","price":"${'$'}34952.01","isAd":true},
            {"id":6,"make":"Ford","model":"Expedition","price":"${'$'}48687.72"},
            {"id":7,"make":"Mercury","model":"Villager","price":"${'$'}87142.83"},
            {"id":8,"make":"Mazda","model":"B-Series Plus","price":"${'$'}6951.24"},
            {"id":9,"make":"Toyota","model":"Land Cruiser","price":"${'$'}69987.52"},
            {"id":10,"make":"Lexus","model":"GX","price":"${'$'}35436.00","isAd":true},
            {"id":11,"make":"Eagle","model":"Summit","price":"${'$'}91844.44","isAd":false},
            {"id":12,"make":"Aston Martin","model":"DB9 Volante","price":"${'$'}41377.45","isAd":false},
            {"id":13,"make":"Honda","model":"Accord","price":"${'$'}71450.53"},
            {"id":14,"make":"Acura","model":"RL","price":"${'$'}40125.13"},
            {"id":15,"make":"Buick","model":"LeSabre","price":"${'$'}86858.67"},
            {"id":16,"make":"Isuzu","model":"Hombre","price":"${'$'}76593.76"},
            {"id":17,"make":"Mitsubishi","model":"Montero","price":"${'$'}13896.16"},
            {"id":18,"make":"Dodge","model":"Avenger","price":"${'$'}43466.12"},
            {"id":19,"make":"Audi","model":"TT","price":"${'$'}31160.45"},
            {"id":20,"make":"Subaru","model":"Impreza","price":"${'$'}72914.14"},
            {"id":21,"make":"Nissan","model":"350Z","price":"${'$'}46542.52"},
            {"id":22,"make":"Chevrolet","model":"Impala","price":"${'$'}61815.90"},
            {"id":23,"make":"Kia","model":"Rio","price":"${'$'}73678.46"},
            {"id":24,"make":"Subaru","model":"Outback","price":"${'$'}60452.65","isAd":false},
            {"id":25,"make":"Mercedes-Benz","model":"E-Class","price":"${'$'}52772.67"},
            {"id":26,"make":"Dodge","model":"Journey","price":"${'$'}9703.49","isAd":false},
            {"id":27,"make":"Volkswagen","model":"GTI","price":"${'$'}72178.36"},
            {"id":28,"make":"Dodge","model":"Intrepid","price":"${'$'}11005.06"},
            {"id":29,"make":"Ford","model":"F-Series","price":"${'$'}16570.47"},
            {"id":30,"make":"Ford","model":"Tempo","price":"${'$'}44932.57","isAd":false},
            {"id":31,"make":"Pontiac","model":"Sunfire","price":"${'$'}22288.22"},
            {"id":32,"make":"Suzuki","model":"Sidekick","price":"${'$'}7912.79"},
            {"id":33,"make":"Honda","model":"Prelude","price":"${'$'}11540.51"},
            {"id":34,"make":"BMW","model":"Z4","price":"${'$'}93112.67"},
            {"id":35,"make":"BMW","model":"Z3","price":"${'$'}98753.00"},
            {"id":36,"make":"Isuzu","model":"Hombre","price":"${'$'}28409.70"},
            {"id":37,"make":"Dodge","model":"D250","price":"${'$'}19723.16","isAd":false},
            {"id":38,"make":"Volvo","model":"V50","price":"${'$'}70579.70"},
            {"id":39,"make":"Ford","model":"Focus","price":"${'$'}22473.61"},
            {"id":40,"make":"Oldsmobile","model":"Alero","price":"${'$'}27973.98","isAd":true},
            {"id":41,"make":"Toyota","model":"4Runner","price":"${'$'}95877.77"},
            {"id":42,"make":"Oldsmobile","model":"Bravada","price":"${'$'}14288.45"},
            {"id":43,"make":"Plymouth","model":"Roadrunner","price":"${'$'}56462.30"},
            {"id":44,"make":"Chrysler","model":"Imperial","price":"${'$'}73182.43"},
            {"id":45,"make":"Audi","model":"A4","price":"${'$'}32334.32"},
            {"id":46,"make":"Audi","model":"R8","price":"${'$'}56943.15"},
            {"id":47,"make":"Ford","model":"F350","price":"${'$'}39031.57","isAd":true},
            {"id":48,"make":"Mitsubishi","model":"Chariot","price":"${'$'}85813.45"},
            {"id":49,"make":"Hyundai","model":"Veracruz","price":"${'$'}61096.82"},
            {"id":50,"make":"Volkswagen","model":"Jetta","price":"${'$'}9195.96"},
            {"id":51,"make":"Chrysler","model":"Sebring","price":"${'$'}72701.91"},
            {"id":52,"make":"Volvo","model":"S40","price":"${'$'}44930.46","isAd":true},
            {"id":53,"make":"Plymouth","model":"Neon","price":"${'$'}12129.46"},
            {"id":54,"make":"Infiniti","model":"I","price":"${'$'}33745.68"},
            {"id":55,"make":"Mercedes-Benz","model":"M-Class","price":"${'$'}72480.47"},
            {"id":56,"make":"Audi","model":"A5","price":"${'$'}28286.25","isAd":true},
            {"id":57,"make":"Plymouth","model":"Voyager","price":"${'$'}30882.42"},
            {"id":58,"make":"Toyota","model":"Prius","price":"${'$'}97742.74","isAd":false},
            {"id":59,"make":"Kia","model":"Rio","price":"${'$'}86806.84"},
            {"id":60,"make":"Lexus","model":"SC","price":"${'$'}12581.03","isAd":false},
            {"id":61,"make":"Mazda","model":"Familia","price":"${'$'}63619.71","isAd":true},
            {"id":62,"make":"GMC","model":"Terrain","price":"${'$'}86828.69","isAd":true},
            {"id":63,"make":"Maybach","model":"62","price":"${'$'}33430.54"},
            {"id":64,"make":"Ford","model":"F250","price":"${'$'}84179.58"},
            {"id":65,"make":"Oldsmobile","model":"88","price":"${'$'}97956.97"},
            {"id":66,"make":"Acura","model":"MDX","price":"${'$'}98414.93"},
            {"id":67,"make":"Mazda","model":"B-Series Plus","price":"${'$'}44724.89"},
            {"id":68,"make":"Chrysler","model":"Town & Country","price":"${'$'}50249.91"},
            {"id":69,"make":"Oldsmobile","model":"Aurora","price":"${'$'}17292.15"},
            {"id":70,"make":"Chevrolet","model":"Malibu","price":"${'$'}97720.08"},
            {"id":71,"make":"Chrysler","model":"300","price":"${'$'}13023.00","isAd":true},
            {"id":72,"make":"Saab","model":"9-3","price":"${'$'}27389.41","isAd":true},
            {"id":73,"make":"Toyota","model":"Yaris","price":"${'$'}7506.03"},
            {"id":74,"make":"Kia","model":"Sportage","price":"${'$'}95945.67"},
            {"id":75,"make":"GMC","model":"Yukon XL 2500","price":"${'$'}59037.47"},
            {"id":76,"make":"Oldsmobile","model":"98","price":"${'$'}46584.85","isAd":true},
            {"id":77,"make":"Honda","model":"Insight","price":"${'$'}48715.38"},
            {"id":78,"make":"Cadillac","model":"Escalade ESV","price":"${'$'}30536.53"},
            {"id":79,"make":"GMC","model":"Suburban 1500","price":"${'$'}25647.38"},
            {"id":80,"make":"Pontiac","model":"Torrent","price":"${'$'}15439.71"},
            {"id":81,"make":"Toyota","model":"Prius Plug-in","price":"${'$'}59609.02"},
            {"id":82,"make":"Subaru","model":"Forester","price":"${'$'}6839.93","isAd":false},
            {"id":83,"make":"Toyota","model":"T100","price":"${'$'}33637.65"},
            {"id":84,"make":"Pontiac","model":"Vibe","price":"${'$'}90727.45"},
            {"id":85,"make":"Volvo","model":"C70","price":"${'$'}78608.12"},
            {"id":86,"make":"Honda","model":"Fit","price":"${'$'}81690.08","isAd":true},
            {"id":87,"make":"GMC","model":"Vandura G3500","price":"${'$'}21807.97"},
            {"id":88,"make":"Ford","model":"Ranger","price":"${'$'}28794.14"},
            {"id":89,"make":"Mazda","model":"Mazda5","price":"${'$'}58572.80"},
            {"id":90,"make":"Toyota","model":"TundraMax","price":"${'$'}66823.26"},
            {"id":91,"make":"Mercury","model":"Cougar","price":"${'$'}30476.19"},
            {"id":92,"make":"Mercury","model":"Cougar","price":"${'$'}9840.89"},
            {"id":93,"make":"Buick","model":"Electra","price":"${'$'}56452.26"},
            {"id":94,"make":"Ford","model":"Crown Victoria","price":"${'$'}60591.30","isAd":true},
            {"id":95,"make":"Volkswagen","model":"Touareg","price":"${'$'}16050.34"},
            {"id":96,"make":"Pontiac","model":"Torrent","price":"${'$'}20780.03","isAd":true},
            {"id":97,"make":"Mitsubishi","model":"Lancer","price":"${'$'}83130.09"},
            {"id":98,"make":"Saab","model":"900","price":"${'$'}48723.56"},
            {"id":99,"make":"Jeep","model":"Liberty","price":"${'$'}33168.43"},
            {"id":100,"make":"Ford","model":"Falcon","price":"${'$'}88807.81"},
            {"id":101,"make":"Infiniti","model":"Q","price":"${'$'}8874.14"},
            {"id":102,"make":"Dodge","model":"Spirit","price":"${'$'}14055.56"},
            {"id":103,"make":"Buick","model":"Park Avenue","price":"${'$'}78431.28"},
            {"id":104,"make":"Ford","model":"E-Series","price":"${'$'}51209.23"},
            {"id":105,"make":"Dodge","model":"Dynasty","price":"${'$'}63969.21"},
            {"id":106,"make":"Acura","model":"RDX","price":"${'$'}49163.26"},
            {"id":107,"make":"Lincoln","model":"Navigator L","price":"${'$'}59361.47"},
            {"id":108,"make":"Bentley","model":"Arnage","price":"${'$'}82910.97"},
            {"id":109,"make":"Acura","model":"SLX","price":"${'$'}22002.71"},
            {"id":110,"make":"Mazda","model":"Miata MX-5","price":"${'$'}11690.78"},
            {"id":111,"make":"Ford","model":"Explorer Sport Trac","price":"${'$'}53943.63"},
            {"id":112,"make":"Mercedes-Benz","model":"SL-Class","price":"${'$'}52145.36"},
            {"id":113,"make":"Buick","model":"Coachbuilder","price":"${'$'}53994.25","isAd":false},
            {"id":114,"make":"Nissan","model":"Frontier","price":"${'$'}23485.84"},
            {"id":115,"make":"Ford","model":"Aerostar","price":"${'$'}70178.91"},
            {"id":116,"make":"Honda","model":"Civic","price":"${'$'}77095.08"},
            {"id":117,"make":"Mazda","model":"Protege","price":"${'$'}51827.85"},
            {"id":118,"make":"Audi","model":"A6","price":"${'$'}23461.64"},
            {"id":119,"make":"Mercedes-Benz","model":"SLR McLaren","price":"${'$'}38433.59"},
            {"id":120,"make":"Buick","model":"Terraza","price":"${'$'}58239.99"},
            {"id":121,"make":"Mercury","model":"Montego","price":"${'$'}85844.83"},
            {"id":122,"make":"Lexus","model":"CT","price":"${'$'}34797.56"},
            {"id":123,"make":"GMC","model":"Savana 1500","price":"${'$'}72239.18"},
            {"id":124,"make":"Dodge","model":"Ram 3500","price":"${'$'}9084.84"},
            {"id":125,"make":"Volkswagen","model":"Passat","price":"${'$'}90446.32"},
            {"id":126,"make":"Subaru","model":"Legacy","price":"${'$'}73137.75"},
            {"id":127,"make":"Suzuki","model":"Aerio","price":"${'$'}59916.27","isAd":false},
            {"id":128,"make":"Mazda","model":"RX-7","price":"${'$'}64867.26"},
            {"id":129,"make":"Subaru","model":"Legacy","price":"${'$'}6112.97"},
            {"id":130,"make":"Volkswagen","model":"Golf","price":"${'$'}75328.24"},
            {"id":131,"make":"Toyota","model":"Land Cruiser","price":"${'$'}82080.81"},
            {"id":132,"make":"Jaguar","model":"X-Type","price":"${'$'}40290.95","isAd":true},
            {"id":133,"make":"Suzuki","model":"SX4","price":"${'$'}22536.57"},
            {"id":134,"make":"Land Rover","model":"Discovery","price":"${'$'}86120.56"},
            {"id":135,"make":"Hyundai","model":"XG300","price":"${'$'}90918.49"},
            {"id":136,"make":"Buick","model":"Electra","price":"${'$'}48900.66"},
            {"id":137,"make":"Toyota","model":"RAV4","price":"${'$'}87266.99"},
            {"id":138,"make":"Volkswagen","model":"Touareg","price":"${'$'}63593.56"},
            {"id":139,"make":"Lotus","model":"Exige","price":"${'$'}83872.54"},
            {"id":140,"make":"Maserati","model":"GranTurismo","price":"${'$'}80671.47"},
            {"id":141,"make":"Ford","model":"Escape","price":"${'$'}96136.13"},
            {"id":142,"make":"Pontiac","model":"Safari","price":"${'$'}98032.12"},
            {"id":143,"make":"Buick","model":"Century","price":"${'$'}23897.39"},
            {"id":144,"make":"Jaguar","model":"S-Type","price":"${'$'}66497.08"},
            {"id":145,"make":"Mercury","model":"Sable","price":"${'$'}17859.14"},
            {"id":146,"make":"Mercedes-Benz","model":"E-Class","price":"${'$'}93711.80","isAd":true},
            {"id":147,"make":"Toyota","model":"Sequoia","price":"${'$'}52560.15"},
            {"id":148,"make":"Ford","model":"GT500","price":"${'$'}31298.22","isAd":false},
            {"id":149,"make":"Volkswagen","model":"Jetta","price":"${'$'}44269.06"},
            {"id":150,"make":"Maybach","model":"57","price":"${'$'}60107.71"},
            {"id":151,"make":"Mazda","model":"Protege","price":"${'$'}37130.09"},
            {"id":152,"make":"Mitsubishi","model":"Pajero","price":"${'$'}20371.35"},
            {"id":153,"make":"GMC","model":"Savana 2500","price":"${'$'}21472.66","isAd":true},
            {"id":154,"make":"Honda","model":"Insight","price":"${'$'}73215.11"},
            {"id":155,"make":"Ford","model":"Escort","price":"${'$'}20542.30"},
            {"id":156,"make":"Ford","model":"Laser","price":"${'$'}41661.90","isAd":true},
            {"id":157,"make":"Subaru","model":"Tribeca","price":"${'$'}9611.15"},
            {"id":158,"make":"Lamborghini","model":"Gallardo","price":"${'$'}35492.49"},
            {"id":159,"make":"Buick","model":"Century","price":"${'$'}10774.78"},
            {"id":160,"make":"Mercury","model":"Villager","price":"${'$'}20342.59"},
            {"id":161,"make":"Ford","model":"Excursion","price":"${'$'}22242.85"},
            {"id":162,"make":"Chevrolet","model":"S10","price":"${'$'}47741.45"},
            {"id":163,"make":"Mercury","model":"Sable","price":"${'$'}8800.11"},
            {"id":164,"make":"Chrysler","model":"Voyager","price":"${'$'}26304.93"},
            {"id":165,"make":"Volkswagen","model":"Touareg","price":"${'$'}12595.57"},
            {"id":166,"make":"Dodge","model":"Ram 3500","price":"${'$'}46664.12"},
            {"id":167,"make":"Lexus","model":"IS","price":"${'$'}26764.76","isAd":true},
            {"id":168,"make":"GMC","model":"Savana","price":"${'$'}27931.56"},
            {"id":169,"make":"Maserati","model":"430","price":"${'$'}32915.51"},
            {"id":170,"make":"Dodge","model":"Ram 3500","price":"${'$'}13423.57"},
            {"id":171,"make":"Cadillac","model":"DeVille","price":"${'$'}91818.21","isAd":true},
            {"id":172,"make":"Mercury","model":"Tracer","price":"${'$'}10474.53"},
            {"id":173,"make":"Jeep","model":"Grand Cherokee","price":"${'$'}10494.92"},
            {"id":174,"make":"Isuzu","model":"Rodeo","price":"${'$'}25215.04"},
            {"id":175,"make":"Kia","model":"Rio","price":"${'$'}21413.76"},
            {"id":176,"make":"Acura","model":"TL","price":"${'$'}77044.46"},
            {"id":177,"make":"Chevrolet","model":"Suburban 1500","price":"${'$'}5564.75"},
            {"id":178,"make":"Ford","model":"Focus","price":"${'$'}49462.49"},
            {"id":179,"make":"Chevrolet","model":"Express","price":"${'$'}10844.63"},
            {"id":180,"make":"Mitsubishi","model":"Galant","price":"${'$'}46189.69"},
            {"id":181,"make":"Ford","model":"Taurus","price":"${'$'}95190.49"},
            {"id":182,"make":"Mazda","model":"Protege","price":"${'$'}27470.20","isAd":false},
            {"id":183,"make":"Tesla","model":"Roadster","price":"${'$'}22187.21"},
            {"id":184,"make":"Ford","model":"E150","price":"${'$'}16022.55"},
            {"id":185,"make":"Chevrolet","model":"Suburban","price":"${'$'}46253.79"},
            {"id":186,"make":"Lincoln","model":"LS","price":"${'$'}80209.27"},
            {"id":187,"make":"Toyota","model":"Avalon","price":"${'$'}64060.44","isAd":true},
            {"id":188,"make":"Lincoln","model":"MKX","price":"${'$'}18189.20"},
            {"id":189,"make":"Infiniti","model":"Q","price":"${'$'}54238.28"},
            {"id":190,"make":"Chevrolet","model":"S10","price":"${'$'}61548.40"},
            {"id":191,"make":"Acura","model":"RDX","price":"${'$'}79004.55"},
            {"id":192,"make":"Honda","model":"CR-V","price":"${'$'}26186.69"},
            {"id":193,"make":"Acura","model":"RDX","price":"${'$'}88986.59","isAd":true},
            {"id":194,"make":"Subaru","model":"Legacy","price":"${'$'}80715.27","isAd":false},
            {"id":195,"make":"Dodge","model":"Ram 3500","price":"${'$'}89577.43"},
            {"id":196,"make":"Ford","model":"Mustang","price":"${'$'}90327.25"},
            {"id":197,"make":"Saab","model":"9-7X","price":"${'$'}6643.39"},
            {"id":198,"make":"Mazda","model":"Mazda5","price":"${'$'}57754.90"},
            {"id":199,"make":"Chevrolet","model":"Equinox","price":"${'$'}60874.36"},
            {"id":200,"make":"Dodge","model":"Stratus","price":"${'$'}73317.62","isAd":false},
            {"id":201,"make":"Chevrolet","model":"Tahoe","price":"${'$'}41852.25"},
            {"id":202,"make":"Honda","model":"Odyssey","price":"${'$'}98699.52"},
            {"id":203,"make":"Mitsubishi","model":"L300","price":"${'$'}26850.58"},
            {"id":204,"make":"Lincoln","model":"Continental Mark VII","price":"${'$'}38011.46"},
            {"id":205,"make":"Mercury","model":"Mariner","price":"${'$'}75202.51"},
            {"id":206,"make":"Ford","model":"Explorer","price":"${'$'}62992.61"},
            {"id":207,"make":"Geo","model":"Prizm","price":"${'$'}69116.01"},
            {"id":208,"make":"Buick","model":"Park Avenue","price":"${'$'}95020.05"},
            {"id":209,"make":"Dodge","model":"Caravan","price":"${'$'}34237.05"},
            {"id":210,"make":"Chevrolet","model":"Express 3500","price":"${'$'}52979.93","isAd":true},
            {"id":211,"make":"Lincoln","model":"LS","price":"${'$'}42194.42"},
            {"id":212,"make":"Jaguar","model":"XJ Series","price":"${'$'}55644.34","isAd":true},
            {"id":213,"make":"Volkswagen","model":"Cabriolet","price":"${'$'}5138.43"},
            {"id":214,"make":"Porsche","model":"Cayman","price":"${'$'}13909.66"},
            {"id":215,"make":"Lexus","model":"SC","price":"${'$'}69673.67"},
            {"id":216,"make":"Chevrolet","model":"Monte Carlo","price":"${'$'}95672.19"},
            {"id":217,"make":"Dodge","model":"D250","price":"${'$'}66008.94"},
            {"id":218,"make":"Dodge","model":"Challenger","price":"${'$'}93890.06"},
            {"id":219,"make":"Mercedes-Benz","model":"Sprinter","price":"${'$'}45022.37"},
            {"id":220,"make":"Lexus","model":"RX Hybrid","price":"${'$'}70402.60"},
            {"id":221,"make":"Ford","model":"Edge","price":"${'$'}90245.45"},
            {"id":222,"make":"Lexus","model":"SC","price":"${'$'}58820.29"},
            {"id":223,"make":"Dodge","model":"Caravan","price":"${'$'}82845.79"},
            {"id":224,"make":"Saab","model":"9000","price":"${'$'}84859.05","isAd":false},
            {"id":225,"make":"Ford","model":"F350","price":"${'$'}12642.52"},
            {"id":226,"make":"Ford","model":"Crown Victoria","price":"${'$'}45827.82"},
            {"id":227,"make":"Volvo","model":"850","price":"${'$'}34181.80"},
            {"id":228,"make":"Chevrolet","model":"Sportvan G30","price":"${'$'}23155.65"},
            {"id":229,"make":"Toyota","model":"Camry","price":"${'$'}25683.33"},
            {"id":230,"make":"Mercedes-Benz","model":"E-Class","price":"${'$'}24469.76","isAd":false},
            {"id":231,"make":"Mercedes-Benz","model":"SLK-Class","price":"${'$'}50410.79","isAd":false},
            {"id":232,"make":"Ford","model":"Escort","price":"${'$'}33000.04"},
            {"id":233,"make":"Dodge","model":"Daytona","price":"${'$'}31410.05"},
            {"id":234,"make":"Dodge","model":"Ram 2500","price":"${'$'}84595.94"},
            {"id":235,"make":"Ford","model":"E250","price":"${'$'}13520.21"},
            {"id":236,"make":"Mazda","model":"Miata MX-5","price":"${'$'}44674.37","isAd":false},
            {"id":237,"make":"Honda","model":"Pilot","price":"${'$'}73880.92","isAd":false},
            {"id":238,"make":"Mitsubishi","model":"Mighty Max Macro","price":"${'$'}30487.34","isAd":false},
            {"id":239,"make":"Mazda","model":"Mazda6","price":"${'$'}92098.80"},
            {"id":240,"make":"Bentley","model":"Continental GTC","price":"${'$'}31746.05"},
            {"id":241,"make":"Chevrolet","model":"Express 1500","price":"${'$'}75720.37"},
            {"id":242,"make":"Geo","model":"Tracker","price":"${'$'}28818.11","isAd":false},
            {"id":243,"make":"Ford","model":"Explorer Sport Trac","price":"${'$'}95006.74"},
            {"id":244,"make":"Audi","model":"S4","price":"${'$'}33083.66"},
            {"id":245,"make":"Honda","model":"Fit","price":"${'$'}76387.51"},
            {"id":246,"make":"Toyota","model":"Prius","price":"${'$'}34113.13"},
            {"id":247,"make":"Mercedes-Benz","model":"S-Class","price":"${'$'}93892.56"},
            {"id":248,"make":"Ford","model":"Mustang","price":"${'$'}24547.23"},
            {"id":249,"make":"Ford","model":"Ranger","price":"${'$'}44464.77","isAd":true},
            {"id":250,"make":"Honda","model":"Pilot","price":"${'$'}88590.49","isAd":false},
            {"id":251,"make":"Cadillac","model":"Escalade ESV","price":"${'$'}33178.96","isAd":true},
            {"id":252,"make":"Cadillac","model":"SRX","price":"${'$'}62798.47"},
            {"id":253,"make":"Nissan","model":"Frontier","price":"${'$'}12586.24"},
            {"id":254,"make":"Lotus","model":"Exige","price":"${'$'}31227.49","isAd":false},
            {"id":255,"make":"Dodge","model":"Grand Caravan","price":"${'$'}68424.98"},
            {"id":256,"make":"Toyota","model":"Tacoma Xtra","price":"${'$'}99192.31"},
            {"id":257,"make":"BMW","model":"X5","price":"${'$'}16158.22"},
            {"id":258,"make":"Mercedes-Benz","model":"500SEL","price":"${'$'}7909.05"},
            {"id":259,"make":"Toyota","model":"Sienna","price":"${'$'}74822.92"},
            {"id":260,"make":"Volkswagen","model":"Jetta","price":"${'$'}86076.31"},
            {"id":261,"make":"Hyundai","model":"Elantra","price":"${'$'}74350.51"},
            {"id":262,"make":"Hyundai","model":"Elantra","price":"${'$'}34205.17"},
            {"id":263,"make":"Nissan","model":"Altima","price":"${'$'}5313.67"},
            {"id":264,"make":"Volkswagen","model":"Cabriolet","price":"${'$'}36307.74"},
            {"id":265,"make":"Chevrolet","model":"Camaro","price":"${'$'}42066.29"},
            {"id":266,"make":"BMW","model":"525","price":"${'$'}74225.60"},
            {"id":267,"make":"BMW","model":"645","price":"${'$'}71669.95"},
            {"id":268,"make":"Honda","model":"Accord","price":"${'$'}32283.48"},
            {"id":269,"make":"Chrysler","model":"New Yorker","price":"${'$'}18944.61"},
            {"id":270,"make":"Lexus","model":"LS","price":"${'$'}42900.22"},
            {"id":271,"make":"Ford","model":"Bronco","price":"${'$'}43425.29"},
            {"id":272,"make":"Acura","model":"TL","price":"${'$'}92883.53"},
            {"id":273,"make":"GMC","model":"Rally Wagon 3500","price":"${'$'}95341.59"},
            {"id":274,"make":"Isuzu","model":"Hombre","price":"${'$'}10536.19"},
            {"id":275,"make":"Toyota","model":"Corolla","price":"${'$'}54599.49"},
            {"id":276,"make":"Bugatti","model":"Veyron","price":"${'$'}94303.21","isAd":false},
            {"id":277,"make":"GMC","model":"Yukon XL 2500","price":"${'$'}59474.28"},
            {"id":278,"make":"Ford","model":"Ranger","price":"${'$'}48209.33"},
            {"id":279,"make":"Suzuki","model":"Forenza","price":"${'$'}55192.06","isAd":false},
            {"id":280,"make":"Lexus","model":"SC","price":"${'$'}98780.02","isAd":true},
            {"id":281,"make":"BMW","model":"5 Series","price":"${'$'}53505.06"},
            {"id":282,"make":"Lamborghini","model":"Diablo","price":"${'$'}92216.56"},
            {"id":283,"make":"Dodge","model":"Dakota","price":"${'$'}28899.77"},
            {"id":284,"make":"Plymouth","model":"Laser","price":"${'$'}82309.12"},
            {"id":285,"make":"Chevrolet","model":"Camaro","price":"${'$'}62211.55"},
            {"id":286,"make":"Lincoln","model":"MKT","price":"${'$'}55417.45"},
            {"id":287,"make":"Honda","model":"Odyssey","price":"${'$'}74782.97"},
            {"id":288,"make":"GMC","model":"Yukon XL 2500","price":"${'$'}7975.89"},
            {"id":289,"make":"Chevrolet","model":"Silverado 2500","price":"${'$'}99656.04","isAd":false},
            {"id":290,"make":"Mazda","model":"Miata MX-5","price":"${'$'}60555.03"},
            {"id":291,"make":"Chevrolet","model":"Cavalier","price":"${'$'}77567.30"},
            {"id":292,"make":"Mercury","model":"Topaz","price":"${'$'}81193.64","isAd":false},
            {"id":293,"make":"Mazda","model":"CX-7","price":"${'$'}23969.78"},
            {"id":294,"make":"Mazda","model":"B-Series","price":"${'$'}87394.67"},
            {"id":295,"make":"GMC","model":"Rally Wagon G3500","price":"${'$'}21595.79"},
            {"id":296,"make":"Foose","model":"Hemisfear","price":"${'$'}77384.35"},
            {"id":297,"make":"Lexus","model":"RX Hybrid","price":"${'$'}71589.42"},
            {"id":298,"make":"Honda","model":"Odyssey","price":"${'$'}28994.76"},
            {"id":299,"make":"Honda","model":"Pilot","price":"${'$'}20702.51"},
            {"id":300,"make":"Mitsubishi","model":"Truck","price":"${'$'}83277.49","isAd":true}]
        """.trimIndent()
    }
}