import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect } from 'react';
import AdList from '../components/ad/AdList';
import AdService from '../services/AdService';

const Homepage = () => {
  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, isLoading } = useInfiniteQuery({
    queryKey: ['ads'],
    queryFn: AdService.getAllPaginated,
    getNextPageParam: (lastPage, allPages) => {
      if (!lastPage.last) {
        return allPages.length;
      }
      return undefined;
    }
  });

  const ads = data?.pages.flatMap((page) => page.content) ?? [];

  // 🔥 Sayfa küçükse manuel olarak sonraki sayfayı çek
  useEffect(() => {
    const checkScrollAndFetch = () => {
      const contentHeight = document.body.scrollHeight;
      const viewportHeight = window.innerHeight;

      if (hasNextPage && contentHeight <= viewportHeight && !isFetchingNextPage) {
        fetchNextPage();
      }
    };

    checkScrollAndFetch();
  }, [ads.length, hasNextPage, isFetchingNextPage, fetchNextPage]);

  return (
    <AdList
      adList={ads}
      fetchNextPage={fetchNextPage}
      hasNextPage={hasNextPage}
      isFetchingNextPage={isFetchingNextPage}
    />
  );
};

export default Homepage;
