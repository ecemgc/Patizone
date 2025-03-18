import AdList from '../components/ad/AdList';
import Navbar from '../components/Navbar';

function Homepage() {
  return (
    <div>
      <Navbar />
      <AdList adList={[123, 345, 324, 459, 678, 789, 678]} />
    </div>
  );
}

export default Homepage;
