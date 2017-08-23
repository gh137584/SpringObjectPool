package it.blog.springobjectpool;
import java.util.List;
import it.blog.springobjectpool.response.ResultRest;
import org.apache.log4j.Logger;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestControllerpublic 
class GatewayController { 
  final static Logger log = Logger.getLogger("GTWLOGGER");  
  @Autowired 
  @Qualifier("poolTargetSourceFileUtil") 
  CommonsPool2TargetSource poolTargetSourceFileUtil;  
  
  @RequestMapping(value = "/file", method = { RequestMethod.GET, RequestMethod.POST }) 
  public List<String> file(@RequestParam("filename") String filename) {   
    try {     
      /*    
      * Return Object from Object Pool    
      */   
      FileUtil simpleBean = (FileUtil)poolTargetSourceFileUtil.getTarget();     
      log.info("Object Hashcode:" + simpleBean.hashCode());   
      /*    
      * Look for the file name contained     
      */  
      List<String> result = simpleBean.getFileByName(filename);      
      log.info("Found:" + result);   
      /*    
      * Release the object      
      */  
      poolTargetSourceFileUtil.releaseTarget(simpleBean);     
      return result;  
    } catch (Exception e) {   
      log.error("file", e);   
      return null;  
    }   
  }
}
