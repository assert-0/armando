<script>
import Armory from "armory-sdk/src/Armory";
import DisplayInterface from "@/interfaces/DisplayInterface";
import RateInterface from "@/interfaces/RateInterface";
export default {
  name: "App",
  extends: Armory,
  components: {
    DisplayInterface,
    RateInterface,
  },
};
</script>
