<script>
import Armory from "armory-sdk/src/Armory";
import DisplayInterface from "@/interfaces/DisplayInterface";
import RateInterface from "@/interfaces/RateInterface";
import DateInterface from "@/interfaces/DateInterface";
export default {
  name: "App",
  extends: Armory,
  components: {
    DisplayInterface,
    RateInterface,
    DateInterface,
  },
};
</script>
