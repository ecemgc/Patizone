import { Box } from '@mui/material';
import Grid from '@mui/material/Grid2';
import Spinner from '../Spinner';
import Ad from './Ad';

function AdList({ adList }) {
  return (
    <Spinner active={!adList}>
      <Box sx={{ flexGrow: 1 }}>
        <Grid container spacing={2}>
          {adList.map((ad) => (
            <Grid size={4}>
              <Ad ad={ad} />
            </Grid>
          ))}
        </Grid>
      </Box>
    </Spinner>
  );
}

export default AdList;
