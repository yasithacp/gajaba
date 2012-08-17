import com.sun.enterprise.ee.cms.core.*;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yasitha
 * Date: 8/16/12
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TableGenerator {

    public String getHtml() {

        Map<String, String> map = this.getDistributedcache();

        String html = "<table>\n" +
                "<tr>\n" +
                "<th>GMSMember</th>\n" +
                "<th>Key</th>\n" +
                "<th>Value</th>\n" +
                "</tr>\n";

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            GMSCacheable key = (GMSCacheable) pairs.getKey();
            System.out.println(key.getComponentName());
            it.remove(); // avoids a ConcurrentModificationException
        }
        html += "</table>";
        return html;
//        return "<table>\n" +
//                "<tr>\n" +
//                "<td>row 1, cell 1</td>\n" +
//                "<td>row 1, cell 2</td>\n" +
//                "</tr>\n" +
//                "<tr>\n" +
//                "<td>row 2, cell 1</td>\n" +
//                "<td>row 2, cell 2</td>\n" +
//                "</tr>\n" +
//                "</table>";
    }

    private Map<String, String> getDistributedcache() {

        final String serverName = "server" + System.currentTimeMillis();
        final String groupName = "Group 1";
        GroupManagementService gms = initializeGMS(serverName, groupName);

        DistributedStateCache dsc = gms.getGroupHandle().getDistributedStateCache();
        try {
            dsc.addToCache("Group 1", "instenceName", (Serializable) "key1", (Serializable) "value1");
        } catch (GMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Map myMap = dsc.getAllCache();

        return myMap;
    }

    private GroupManagementService initializeGMS(String serverName, String groupName) {
        return (GroupManagementService) GMSFactory.startGMSModule(serverName,
                groupName, GroupManagementService.MemberType.CORE, null);
    }

    public static void main(String[] args) {
        TableGenerator aa = new TableGenerator();
        aa.getHtml();
    }
}
