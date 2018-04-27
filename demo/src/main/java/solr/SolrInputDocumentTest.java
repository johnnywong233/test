//package solr;
//
//import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.SolrInputDocument;
//import org.apache.solr.common.SolrInputField;
//import org.junit.Test;
//
///**
// * Created by wajian on 2016/10/4.
// * silly test on method of SolrInputDocument
// */
//public class SolrInputDocumentTest {
//    //http://www.cnblogs.com/hoojo/archive/2011/10/21/2220431.html
//    public final void print(Object o) {
//        System.out.println(o);
//    }
//
//    /**
//     * <b>function:</b> create SolrInputDocument
//     */
//    @Test
//    public void createDoc() {
//        SolrInputDocument doc = new SolrInputDocument();
//        doc.addField("id", System.currentTimeMillis());
//        doc.addField("name", "SolrInputDocument");
//        doc.addField("age", 22);
//
//        doc.addField("like", new String[]{"music", "book", "sport"});
//
//        doc.put("address", new SolrInputField("guangzhou"));
//
//        doc.setField("sex", "man");
//        doc.setField("remark", "china people");
//
//        print(doc);
//    }
//
//    /**
//     * <b>function:</b> use DocumentObjectBinder to exchange SolrInputDocument and User
//     */
//    @Test
//    public void docAndBean4Binder() {
//        SolrDocument doc = new SolrDocument();
//        doc.addField("id", 456);
//        doc.addField("name", "SolrInputDocument");
//
//        doc.addField("likes", new String[]{"music", "book", "sport"});
//
//        doc.put("address", "guangzhou");
//
//        doc.setField("sex", "man");
//        doc.setField("remark", "china people");
//
//        DocumentObjectBinder binder = new DocumentObjectBinder();
//
//        User user = new User();
//        user.setId(222);
//        user.setName("JavaBean");
//        user.setLike(new String[]{"music", "book", "sport"});
//        user.setAddress("guangdong");
//
//        print(doc);
//        // User ->> SolrInputDocument
//        print(binder.toSolrInputDocument(user));
//        // SolrDocument ->> User
//        print(binder.getBean(User.class, doc));
//
//        SolrDocumentList list = new SolrDocumentList();
//        list.add(doc);
//        list.add(doc);
//        //SolrDocumentList ->> List
//        print(binder.getBeans(User.class, list));
//    }
//
//    /**
//     * <b>function:</b> method of SolrInputDocument
//     */
//    @Test
//    public void docMethod() {
//        SolrInputDocument doc = new SolrInputDocument();
//        doc.addField("id", System.currentTimeMillis());
//        doc.addField("name", "SolrInputDocument");
//        doc.addField("age", 23);
//        doc.addField("age", 22);
//        doc.addField("age", 24);
//
//        print(doc.entrySet());
//        print(doc.get("age"));
//        //排名有用，类似百度竞价排名
//        doc.setField(2.0f);
//        print(doc.getDocumentBoost());
//        print(doc.getField("name"));
//        print(doc.getFieldNames());//keys
//        print(doc.getFieldValues("age"));
//        print(doc.getFieldValues("id"));
//        print(doc.values());
//    }
//}