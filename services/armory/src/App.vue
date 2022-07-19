<script>
import Armory from "armory-sdk/src/Armory";
import DefaultInterface from "@/interfaces/DefaultInterface";
import StreakInterface from "@/interfaces/StreakInterface";
import CenteredContentInterface from "@/interfaces/CenteredContentInterface";
import OfferInterface from "@/interfaces/OfferInterface";
export default {
  name: "App",
  extends: Armory,
  components: {
    DefaultInterface,
    StreakInterface,
    CenteredContentInterface,
    OfferInterface,
  },
};
</script>
