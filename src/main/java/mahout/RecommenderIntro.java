package mahout;

import java.io.IOException;
import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * Created by wajian on 2016/8/30.
 */
public class RecommenderIntro {

    //http://www.phpxs.com/code/1001937/
    public static void main(String[] args) {
        try {
            //进行数据的装载
            DataModel model = new FileDataModel(new File("E:\\mahout项目\\examples\\intro.csv"));

            UserSimilarity similarity = new org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            //生成推荐引擎
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            //为用户已推荐一件商品recommend( , );其中参数的意思是：第几个人，然后推荐几件商品
            List<RecommendedItem> recommendations = recommender.recommend(1, 1);
            for(RecommendedItem recommendation : recommendations){
                System.out.println("根据您的浏览，为您推荐的商品是：" + recommendation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }
    }
}
