import { Box, Grid } from '@mui/material';
import InfiniteScroll from 'react-infinite-scroll-component';
import Ad from './Ad';

function AdList({ adList, fetchNextPage, hasNextPage, isFetchingNextPage }) {
  return (
    <Box sx={{ flexGrow: 1, padding: 2 }}>
      <InfiniteScroll
        dataLength={adList.length}
        next={fetchNextPage}
        hasMore={hasNextPage}
        loader={<p style={{ textAlign: 'center' }}>Loading...</p>}
        endMessage={
          <p style={{ textAlign: 'center' }}>
            <b>No More Ad.</b>
          </p>
        }>
        <Grid container spacing={3} alignItems="stretch">
          {adList.map((ad) => (
            <Grid item xs={12} sm={6} md={4} lg={3} key={ad.id}>
              <Box sx={{ height: '100%' }}>
                <Ad ad={ad} />
              </Box>
            </Grid>
          ))}
        </Grid>
      </InfiniteScroll>
    </Box>
  );
}

export default AdList;
