package org.example.day11;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(day2(getInput()));
    }

    private static long day1(String input) {
        String[] lines = input.split("\n");
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < lines.length; i++) {
            String[] arr = lines[i].split(": ");
            map.put(arr[0], new ArrayList<>(Arrays.asList(arr[1].split(" "))));
        }
        long result = 0;
        Set<List<String>> set = new HashSet<>();
        List<String> valueSet = map.get("you");

        return countPaths(map, "you", "out");
    }

    private static long day2(String input) {
        String[] lines = input.split("\n");
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < lines.length; i++) {
            String[] arr = lines[i].split(": ");
            map.put(arr[0], new ArrayList<>(Arrays.asList(arr[1].split(" "))));
        }
        Map<String, long[][]> memo = new HashMap<>();

        long count = dfs("svr", false, false, map, memo);

        System.out.println("Total paths visiting BOTH dac & fft: " + count);
        return count;
    }

    private static long dfs(String node,
                            boolean seenFFT,
                            boolean seenDAC,
                            Map<String, List<String>> graph,
                            Map<String, long[][]> memo) {

        if (node.equals("fft")) seenFFT = true;
        if (node.equals("dac")) seenDAC = true;

        if (node.equals("out")) {
            return (seenFFT && seenDAC) ? 1 : 0;
        }

        long[][] dp = memo.computeIfAbsent(node, k -> new long[][]{
                { -1, -1 },
                { -1, -1 }
        });

        int f = seenFFT ? 1 : 0;
        int d = seenDAC ? 1 : 0;

        if (dp[f][d] != -1) {
            return dp[f][d];
        }

        long total = 0;

        List<String> next = graph.get(node);
        if (next != null) {
            for (String nxt : next) {
                total += dfs(nxt, seenFFT, seenDAC, graph, memo);
            }
        }

        dp[f][d] = total;
        return total;
    }



    private static int countPaths(Map<String, List<String>> graph, String start, String target) {
        return dfs(start, target, graph);
    }

    private static int dfs(String current, String target, Map<String, List<String>> graph) {
        if (current.equals(target)) {
            return 1;
        }

        if (!graph.containsKey(current)) {
            return 0;
        }

        int count = 0;
        for (String next : graph.get(current)) {
            count += dfs(next, target, graph);
        }

        return count;
    }


    private static String getExample() {
        return "aaa: you hhh\n" +
                "you: bbb ccc\n" +
                "bbb: ddd eee\n" +
                "ccc: ddd eee fff\n" +
                "ddd: ggg\n" +
                "eee: out\n" +
                "fff: out\n" +
                "ggg: out\n" +
                "hhh: ccc fff iii\n" +
                "iii: out";
    }

    private static String getExample2() {
        return "svr: aaa bbb\n" +
                "aaa: fft\n" +
                "fft: ccc\n" +
                "bbb: tty\n" +
                "tty: ccc\n" +
                "ccc: ddd eee\n" +
                "ddd: hub\n" +
                "hub: fff\n" +
                "eee: dac\n" +
                "dac: fff\n" +
                "fff: ggg hhh\n" +
                "ggg: out\n" +
                "hhh: out";
    }

    private static String getInput() {
        return "shx: usd tnq min tsg shc ilw wje wui iyi jvd lab syu jap lyc utc ees\n" +
                "dke: out\n" +
                "iit: xpy\n" +
                "rwz: nht\n" +
                "fnk: woh vaa hpc aql dfq qct obn vdy otq bbf kjz jbo\n" +
                "azf: ogd xuz oll rzq\n" +
                "ljq: jcx\n" +
                "muz: dnx dac fgb\n" +
                "ejr: foc zdx rmc\n" +
                "ayv: fvz\n" +
                "zkf: wkm mpe zps\n" +
                "odi: ybq jxb\n" +
                "job: que reh twn\n" +
                "crg: qje\n" +
                "jph: yhv\n" +
                "wsq: bnb xzs rgl\n" +
                "wny: zfk sqb\n" +
                "rjz: kmz rvt\n" +
                "rmc: bmr ycq mzw myd rxa uwx bgg two gtu\n" +
                "beg: kxp iwg you\n" +
                "vxz: omz\n" +
                "gkk: fns\n" +
                "nmv: yhn mtv wwk\n" +
                "pry: tea kln dzt jvq pak qqc vuj phb wny\n" +
                "wfb: ckw ogm\n" +
                "bnn: ibq zdx rmc\n" +
                "tqc: zvk\n" +
                "pwx: mgx emq\n" +
                "qct: zsg lgp eoa cqg wfb\n" +
                "eoa: yga ckw rub\n" +
                "yhv: ncm uve mrr bzu\n" +
                "lgp: ckw\n" +
                "dfl: aux\n" +
                "zoi: drr\n" +
                "huu: fns rol\n" +
                "tqz: klf gsz efl\n" +
                "jfp: fns rol uqy\n" +
                "tfp: qqt ebp tqz\n" +
                "bzu: ffm vsu\n" +
                "xiw: rmc foc ibq\n" +
                "mpe: out\n" +
                "hzk: ibq foc\n" +
                "qts: tjb ztv\n" +
                "tto: mkw\n" +
                "bjp: rmc zdx\n" +
                "ubu: anv\n" +
                "wed: iro njj syi nku\n" +
                "mfd: tjb\n" +
                "ckp: xlr qne\n" +
                "kvg: lqa kvy\n" +
                "zvk: ybq pry jxb dmu\n" +
                "rai: cqm rdk\n" +
                "bgg: pur npq\n" +
                "ljb: anv dum hdz\n" +
                "fgh: jqs sqp ept hnx\n" +
                "lwm: piv mgx\n" +
                "amg: qcp\n" +
                "phb: aeu hgi niz bjx\n" +
                "vcv: iec\n" +
                "zsg: ogm ckw rub\n" +
                "ggz: kxp iwg you fnk\n" +
                "rpq: cqm rdk\n" +
                "zey: cpt xiw xjq\n" +
                "lig: aye wtc wbh agg\n" +
                "sqb: xwo\n" +
                "tjb: oqi snu hnn cxp zka eoq sce igg crr kom xvg xea cnn dwd rvv jva\n" +
                "dzt: zuh yhg ocy ubu ljb\n" +
                "vsn: fnk you iwg kxp\n" +
                "ept: qwp\n" +
                "lcj: ugo pkl\n" +
                "tea: hgi aeu bjx niz\n" +
                "oqi: csa umy\n" +
                "bbf: dfl jcu xda sfg\n" +
                "cqm: zpq amg map\n" +
                "cqa: pkl ugo jcx lgk\n" +
                "xdw: fcw olh svq huu nnx\n" +
                "jlb: oxf bjp\n" +
                "iro: rgp\n" +
                "ees: rpm mxd umf\n" +
                "cya: iec bnn\n" +
                "ucp: bzu uve akd mrr\n" +
                "ycq: bnb gav rgl\n" +
                "hcg: whz egh grp\n" +
                "npq: qne xlr koe\n" +
                "hgi: rtq uxh aog sgg\n" +
                "rav: rai\n" +
                "eyb: jft\n" +
                "iua: dnx\n" +
                "edk: trd akj aok\n" +
                "ewv: ocy ubu yhg zuh\n" +
                "drr: lwp\n" +
                "lyc: shp uwq pwx\n" +
                "zjm: cxy jxb\n" +
                "ujq: foc zdx rmc\n" +
                "sfg: qbi\n" +
                "uwk: hos wvu ggd\n" +
                "uio: qbk xqj job\n" +
                "jvk: ept jqs\n" +
                "xex: hzk ipu ugl\n" +
                "igg: wed\n" +
                "nkg: pxc swh\n" +
                "maa: egh whz\n" +
                "rbm: jph xpy clk\n" +
                "ehg: rmc zdx ibq foc\n" +
                "kdo: bno bqe\n" +
                "lrw: uqy\n" +
                "tsg: shp\n" +
                "iri: gav\n" +
                "mgx: yzx\n" +
                "min: rpm mxd\n" +
                "tlq: fns rol uqy\n" +
                "jxr: tnq tsg shc utc\n" +
                "pld: out\n" +
                "grp: ztv\n" +
                "jcx: pch\n" +
                "fgb: buj drr\n" +
                "hcr: omz\n" +
                "zkn: dmu pry jxb ybq\n" +
                "vbi: you fnk kxp\n" +
                "hil: hos wvu ggd\n" +
                "dbx: dmu pry jxb ybq\n" +
                "lvp: yzx\n" +
                "ptf: gkc uio jsu zny\n" +
                "lwp: you fnk iwg kxp\n" +
                "aqt: lhq upa\n" +
                "thg: olc\n" +
                "mvu: uwk sfd qwn ipb\n" +
                "kxp: uvd kjz vaa woh aql ptf qct obn rav hgf otq bbf jbo hpc gsw sgm ywv vdy\n" +
                "ohu: ebp\n" +
                "lzw: znc\n" +
                "ztv: hnn oqi snu zka eoq cxp igg khi fpj sce xvg crr dwd cnn rvv jva vks axk\n" +
                "jtq: oxd\n" +
                "prw: tif zex jkb lwe\n" +
                "ebp: gsz efl klf\n" +
                "map: ima wye qcp\n" +
                "zny: job\n" +
                "glc: fft tuw\n" +
                "uts: tfp zze\n" +
                "ivv: hil ipb qwn\n" +
                "cxp: dms\n" +
                "syu: hct mcf\n" +
                "gjy: jfp omz\n" +
                "zpq: btn qcp wye\n" +
                "sgg: byk\n" +
                "nku: ffr\n" +
                "gzv: lqa kvy\n" +
                "oge: uwk hil ipb qwn\n" +
                "xuz: iwg kxp you\n" +
                "two: idl jvk tqn\n" +
                "gsw: gkc zny uio\n" +
                "wnt: vlj puq azf olc\n" +
                "wbh: uvz ybl\n" +
                "foc: vzq ycq mzw fxv wsq\n" +
                "vgv: mss oti pne tqc\n" +
                "pak: hgi gcq niz\n" +
                "wmb: rdo lig nsv\n" +
                "dwd: uoq\n" +
                "mhi: out\n" +
                "kie: uah nkg\n" +
                "pdg: beg vsn\n" +
                "upa: dnh qim lss\n" +
                "nzr: lvp\n" +
                "fmm: gav bnb\n" +
                "tqn: sqp hnx ozd jqs\n" +
                "hse: cxy dmu pry jxb ybq\n" +
                "fpj: nbz qfe\n" +
                "wqj: vxv akj trd aok\n" +
                "vzx: iju fgd jft\n" +
                "eak: out\n" +
                "jhn: zps\n" +
                "tif: usi wce\n" +
                "hqa: rmc ibq\n" +
                "ihe: wyz\n" +
                "lfp: gzv fsd kvg aix\n" +
                "puq: hir ogd\n" +
                "pch: hqa nnl ydp\n" +
                "anv: mkw suv tlq gkk\n" +
                "jmb: dac fgb\n" +
                "ruo: yzx\n" +
                "mxd: fhp sjx dfy\n" +
                "nbf: ctg pxc swh\n" +
                "uvd: ayv rpq\n" +
                "idl: ozd hnx sqp jqs\n" +
                "mry: ruo mfd nht\n" +
                "ybl: pdf vbi vsn\n" +
                "yvt: dmr kdo yyb osw\n" +
                "brq: vqg wzq lqv pqy\n" +
                "hgf: rpq\n" +
                "lhq: foe lss\n" +
                "nor: jim\n" +
                "ruk: lgh\n" +
                "fsd: lqa gkv\n" +
                "ziu: tjb\n" +
                "yhg: dum anv tto\n" +
                "aql: jsu\n" +
                "ugo: rtl ohg sri\n" +
                "enz: fjj uts ees jap lyc ilw usd tnq min iyi wui wje\n" +
                "xpm: fft tuw yss xex\n" +
                "vks: vsh cqa\n" +
                "gkv: fnk iwg kxp\n" +
                "aok: jxb dmu cxy\n" +
                "prc: jph lna xpy\n" +
                "hyp: cya\n" +
                "akj: pry ybq\n" +
                "ukn: rmc ibq zdx\n" +
                "hct: ino qts\n" +
                "lab: mry nzr hje rwz\n" +
                "wce: fns uqy\n" +
                "rfb: xzs\n" +
                "efl: yzx ztv\n" +
                "kln: sqb jim\n" +
                "koe: hbd njh uai ptb\n" +
                "mss: zkn\n" +
                "ozl: ztv\n" +
                "vuj: bjx niz hgi gcq aeu\n" +
                "osw: fvt bno\n" +
                "jkb: wce\n" +
                "jft: out\n" +
                "lss: fwv edk\n" +
                "fgd: out\n" +
                "suv: rol uqy\n" +
                "uer: dnx zoi\n" +
                "ilw: uen umg gco qhj jwi\n" +
                "yga: hvl\n" +
                "qbk: reh twn que\n" +
                "xqj: twn\n" +
                "vdy: uxj rbm prc\n" +
                "shp: piv emq\n" +
                "aam: fcw huu svq nnx\n" +
                "uvz: vbi ewo beg vsn\n" +
                "nbz: nkg\n" +
                "xga: yzx tjb\n" +
                "usd: lwm pwx\n" +
                "uxj: clk jph\n" +
                "trd: pry jxb cxy dmu\n" +
                "otm: vgv qje wyz\n" +
                "jwi: hcg\n" +
                "klf: yzx\n" +
                "iec: zdx ibq\n" +
                "qbi: rjz znc gye\n" +
                "kvy: you fnk kxp\n" +
                "zze: tqz\n" +
                "hjj: rzq xuz ogd\n" +
                "uxh: lrw\n" +
                "ogm: mkm hvl tmd\n" +
                "csa: njj hyp nku\n" +
                "kjz: jcu xda wmd\n" +
                "jsu: syh qbk job\n" +
                "qcp: eak nil pld dke\n" +
                "reh: eru zkf ico dps\n" +
                "ggd: iwg fnk\n" +
                "wyz: mss tqc pne\n" +
                "twn: eru ico dps zkf\n" +
                "gco: maa\n" +
                "caj: odi\n" +
                "sgm: rpq ayv\n" +
                "rzq: kxp fnk\n" +
                "rtq: byk sxw\n" +
                "dnx: drr\n" +
                "bno: iwg\n" +
                "mcj: ibq foc\n" +
                "qwp: dmu cxy pry jxb\n" +
                "axk: ljq cqa vsh\n" +
                "btn: pld nil eak oln dke\n" +
                "ogd: fnk kxp iwg\n" +
                "wkm: out\n" +
                "aku: fhg aam\n" +
                "lgk: ohg\n" +
                "wye: nil\n" +
                "jqz: kvg\n" +
                "fns: vlb wmb eza nmv wnt wrv mxa pbs snn\n" +
                "omz: rol uqy\n" +
                "uur: fns rol uqy\n" +
                "buj: qbz\n" +
                "wzx: yvt wwk mtv\n" +
                "yyb: fvt bno\n" +
                "sxw: fns\n" +
                "gkc: qbk syh xqj job\n" +
                "obn: zsg lgp eoa cqg wfb\n" +
                "lqa: kxp fnk\n" +
                "rub: sgv hvl mkm\n" +
                "gml: ztv\n" +
                "njh: yvf\n" +
                "tbf: gco uen jwi\n" +
                "zis: fax\n" +
                "fwv: vxv\n" +
                "gtu: lhq\n" +
                "iuz: hdx\n" +
                "ptb: txn cpk\n" +
                "hvl: itd\n" +
                "jim: prw xwo\n" +
                "ctg: cpt mcj ujq xjq\n" +
                "cpt: rmc zdx ibq\n" +
                "que: ico dps zkf jhn\n" +
                "ktp: wwk\n" +
                "olh: uqy\n" +
                "vjv: mhi iju\n" +
                "erx: ybq pry\n" +
                "znc: brq nhw dqw rvt kmz\n" +
                "dqw: vqg lqv wzq rxc pqy\n" +
                "qwn: ggd\n" +
                "jsh: rdo\n" +
                "oxd: pry\n" +
                "ncm: pwb ffm vsu\n" +
                "dum: tlq lpq\n" +
                "jxb: lnm jrb kpk kln ewv\n" +
                "sce: hwp maj gpj\n" +
                "czz: nkg nbf\n" +
                "pxc: xjq cpt mcj\n" +
                "ino: tjb\n" +
                "yzx: crr kom xvg xea cnn dwd rvv axk jva oqi snu hnn cxp eoq zka fpj sce igg khi\n" +
                "nil: out\n" +
                "foe: fwv edk lrz\n" +
                "uwq: piv ziu emq lyr\n" +
                "hwp: bjp ukn\n" +
                "xfb: yzx\n" +
                "gav: caj dcs iuz\n" +
                "lna: hfp bje\n" +
                "rdk: amg\n" +
                "nnx: fns\n" +
                "zfk: raq xwo\n" +
                "rvt: vqg wzq lqv\n" +
                "iju: out\n" +
                "umg: hcg tnx\n" +
                "aix: kvy\n" +
                "svr: jxr shx enz uha gjl\n" +
                "dkt: out\n" +
                "gjl: ees uts utc lyc jap syu tbf lab jvd wui wje ilw tsg shc qbx min tnq usd\n" +
                "ihb: fhg xdw\n" +
                "lqv: out\n" +
                "dps: mpe\n" +
                "gpj: bqd ukn\n" +
                "sri: ehg hqa ydp nnl\n" +
                "bnb: dcs caj epv\n" +
                "eno: fvt bqe\n" +
                "swh: mcj xjq\n" +
                "pbs: iua jmb muz\n" +
                "qim: lrz rdb wqj\n" +
                "khi: czz kie nbz\n" +
                "eru: dkt wkm mpe\n" +
                "dms: nbf\n" +
                "byk: rol fns\n" +
                "raq: jkb xde zex tif\n" +
                "oll: fnk\n" +
                "vzq: lhq gjp\n" +
                "zex: wce usi\n" +
                "xjq: ibq\n" +
                "toi: xfb xga qts\n" +
                "oks: aix fsd\n" +
                "lwe: wce\n" +
                "mkw: uqy fns rol\n" +
                "ipb: ggd wvu\n" +
                "uoq: nku njj syi\n" +
                "pwb: out\n" +
                "vlj: hir oll rzq ogd xuz\n" +
                "ief: fns\n" +
                "mig: byk\n" +
                "uen: maa\n" +
                "gcq: rtq mig aog sgg\n" +
                "zdx: and otm ycq iri vzq aqt wsq gtu crg two bgg rxa ihe fmm myd fxv bmr zis rfb\n" +
                "rol: ktp ivv lfp gwh wnt wrv wmb mof mvu thg\n" +
                "exq: zuh yhg ubu ocy\n" +
                "nmf: lig nsv\n" +
                "wmd: lzw\n" +
                "dnh: wqj lrz rdb edk\n" +
                "wui: dvj mxd rpm\n" +
                "yss: ugl hzk ejr\n" +
                "rgl: caj jtq\n" +
                "mxa: hjj\n" +
                "xda: qbi aux lzw\n" +
                "rtx: iju jft fgd\n" +
                "bqe: kxp fnk you\n" +
                "dcs: dbx odi\n" +
                "utr: ckp npq\n" +
                "ozd: ygq qwp\n" +
                "fcw: rol fns uqy\n" +
                "fax: foe\n" +
                "qje: pne tqc\n" +
                "wek: qqt ebp\n" +
                "eza: lig nsv\n" +
                "woh: cqg eoa lgp zsg\n" +
                "dvj: dxc\n" +
                "myd: npq pur\n" +
                "eoq: nbz\n" +
                "ydp: zdx ibq foc rmc\n" +
                "hpc: rbm prc iit\n" +
                "tmd: eyb vzx rtx\n" +
                "njj: cya ffr\n" +
                "ffm: out\n" +
                "clk: yhv bje\n" +
                "zps: out\n" +
                "ckw: hvl tmd wti\n" +
                "hos: kxp iwg\n" +
                "mof: rdo\n" +
                "tnx: whz\n" +
                "yvf: cxy dmu jxb\n" +
                "uah: ctg zey\n" +
                "zka: uoq umy\n" +
                "jcu: qbi\n" +
                "lpq: rol\n" +
                "qqc: qod vxz gjy\n" +
                "akd: vsu pwb\n" +
                "hir: kxp you\n" +
                "and: pur npq\n" +
                "pkl: rtl\n" +
                "qqt: ozl gsz efl\n" +
                "ywv: wmd sfg jcu\n" +
                "sez: ggz lwp qbz\n" +
                "nsv: agg\n" +
                "rtl: ehg nnl\n" +
                "fvz: map\n" +
                "jqs: qwp\n" +
                "cxy: nor kln tea exq pak qqc ihb aku\n" +
                "nhw: vqg pqy rxc\n" +
                "yhn: eno yyb kdo osw\n" +
                "aog: lrw sxw byk\n" +
                "vsu: out\n" +
                "kpk: hcr qod gjy lgh\n" +
                "pur: qne xlr\n" +
                "ocy: hdz tto\n" +
                "kmz: pqy\n" +
                "osb: yhg ljb ubu\n" +
                "ffr: bnn\n" +
                "jva: xpm wml\n" +
                "oti: zvk zjm\n" +
                "wwk: eno yyb\n" +
                "wrv: jhf jmb uer muz iua\n" +
                "nnl: rmc\n" +
                "svq: fns\n" +
                "crr: ljq lcj\n" +
                "tuw: ejr ipu ugl\n" +
                "xpy: ucp yhv bje\n" +
                "syi: vcv\n" +
                "hnx: qwp ygq hse\n" +
                "lvj: hcr qod gjy lgh\n" +
                "wtc: ybl pdg\n" +
                "pne: zvk zjm zkn\n" +
                "uwx: jvk idl tqn\n" +
                "txn: ybq pry cxy\n" +
                "ohg: nnl hqa ydp\n" +
                "xzs: epv caj iuz jtq\n" +
                "otq: iit rbm\n" +
                "lrz: trd akj vxv aok\n" +
                "pqy: out\n" +
                "itd: jft\n" +
                "sgv: eyb vzx vjv rtx\n" +
                "sjx: tjb\n" +
                "mcf: qts xga xfb\n" +
                "jbo: ayv\n" +
                "jap: mry nzr rwz\n" +
                "sfd: wvu hos\n" +
                "niz: rtq mig aog sgg\n" +
                "fft: ugl hzk ejr\n" +
                "mrr: ffm\n" +
                "snn: gzv fsd aix\n" +
                "cnn: xpm glc\n" +
                "xvg: gpj hwp\n" +
                "vlb: hjj vlj\n" +
                "uai: xfz txn erx\n" +
                "oxf: zdx\n" +
                "agg: ybl uvz\n" +
                "lgh: ief\n" +
                "qfe: nbf nkg\n" +
                "wwy: ebp qqt\n" +
                "wml: yss fft\n" +
                "ygq: dmu cxy ybq\n" +
                "hbd: erx xfz yvf\n" +
                "xfz: ybq pry jxb dmu\n" +
                "dxc: yzx\n" +
                "jrb: aeu gcq niz\n" +
                "ugl: zdx ibq rmc\n" +
                "fxv: wyz\n" +
                "ima: nil eak dke oln\n" +
                "cpk: jxb cxy\n" +
                "uha: wui utc ees wje lyc qbx tsg syu fjj usd tnq\n" +
                "hfp: mrr uve ncm\n" +
                "hnn: ljq cqa\n" +
                "ewo: fnk you\n" +
                "utc: jwi qhj uen\n" +
                "dfq: dfl wmd sfg\n" +
                "xlr: hbd njh bdx ptb\n" +
                "gsz: ztv tjb\n" +
                "aeu: mig rtq\n" +
                "wzq: out\n" +
                "jvq: xdw aam\n" +
                "rpm: dxc sqq fhp\n" +
                "vqg: out\n" +
                "rxa: wyz\n" +
                "oln: out\n" +
                "rdo: aye wtc\n" +
                "fhp: ztv tjb yzx\n" +
                "ico: dkt mpe\n" +
                "tnq: pwx uwq lwm\n" +
                "xwo: xde jkb tif\n" +
                "rdb: trd akj aok\n" +
                "hje: ruo gml\n" +
                "ybq: ihb qqc exq lnm lvj tea ruk\n" +
                "qbz: fnk iwg kxp\n" +
                "mzw: jvk idl fgh\n" +
                "ibq: ycq mzw utr aqt and otm two bgg ihe gtu crg myd bmr fmm zis rfb\n" +
                "qne: uai ptb\n" +
                "rxc: out\n" +
                "lnm: jim\n" +
                "hdz: mkw suv lpq\n" +
                "umy: njj\n" +
                "you: obn rav qct otq ptf gsw\n" +
                "iwg: kjz rav obn hgf ptf aql vaa woh dfq bbf jbo otq ywv sgm vdy hpc\n" +
                "nht: yzx tjb ztv\n" +
                "wje: wek zze ohu wwy\n" +
                "qve: kmz nhw\n" +
                "rgp: axv bnn\n" +
                "aux: qve gye\n" +
                "wvu: you fnk\n" +
                "jvd: mcf toi\n" +
                "vsh: lgk\n" +
                "bmr: ckp\n" +
                "emq: ztv tjb yzx\n" +
                "usi: rol fns\n" +
                "syh: twn reh que\n" +
                "bqd: zdx foc rmc\n" +
                "qbx: shp\n" +
                "qod: omz ief\n" +
                "dmu: ewv nor dzt osb ruk tea\n" +
                "fjj: wek tfp wwy zze ohu\n" +
                "sqq: yzx tjb\n" +
                "whz: tjb yzx\n" +
                "snu: glc xpm\n" +
                "kom: uoq wed\n" +
                "ipu: foc rmc\n" +
                "mtv: eno osw\n" +
                "wti: rtx eyb itd vjv vzx\n" +
                "vaa: rbm\n" +
                "jhf: zoi\n" +
                "fhg: olh nnx\n" +
                "egh: ztv tjb yzx\n" +
                "maj: oxf bqd ukn\n" +
                "bje: bzu uve akd ncm\n" +
                "gwh: jhf uer jmb iua\n" +
                "epv: odi dbx\n" +
                "piv: yzx tjb\n" +
                "cqg: yga rub\n" +
                "dac: buj sez drr\n" +
                "axv: foc rmc\n" +
                "xea: jlb\n" +
                "xde: uur\n" +
                "vxv: pry cxy dmu\n" +
                "aye: uvz pdg\n" +
                "qhj: hcg maa\n" +
                "fvt: fnk you kxp iwg\n" +
                "lyr: ztv\n" +
                "uqy: nmf jsh wrv gwh oge eza oks vlb wzx jqz\n" +
                "pdf: kxp iwg fnk\n" +
                "shc: hct\n" +
                "olc: oll hir ogd xuz\n" +
                "rvv: nbz czz dms qfe\n" +
                "mkm: vzx\n" +
                "gjp: qim\n" +
                "dmr: bno fvt\n" +
                "zuh: dum\n" +
                "gye: brq nhw kmz dqw\n" +
                "hdx: jxb cxy dmu\n" +
                "sqp: qwp hse\n" +
                "iyi: nzr mry\n" +
                "umf: fhp dfy\n" +
                "bdx: erx txn yvf\n" +
                "uve: vsu\n" +
                "bjx: aog sgg uxh mig\n" +
                "dfy: tjb ztv";
    }
}
