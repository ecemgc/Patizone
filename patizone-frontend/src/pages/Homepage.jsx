import React from "react";
import Navbar from "../components/Navbar";
import Chat from "../components/chat/Chat";
import AdList from "../components/ad/AdList";
function Homepage() {
  return (
    <div>
      <AdList adList={[123, 345,324,459,678,789,678]} />
    </div>
  );
}

export default Homepage;
